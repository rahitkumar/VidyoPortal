package com.vidyo.bo.authentication;

import static org.springframework.security.saml.util.SAMLUtil.isDateTimeSkewValid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.opensaml.common.SAMLException;
import org.opensaml.common.SAMLObject;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.EncryptedAssertion;
import org.opensaml.saml2.core.EncryptedAttribute;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.saml2.core.StatusMessage;
import org.opensaml.saml2.metadata.AssertionConsumerService;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.DecryptionException;
import org.opensaml.xml.validation.ValidationException;
import org.springframework.security.saml.SAMLConstants;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.SAMLStatusException;
import org.springframework.security.saml.context.SAMLMessageContext;
import org.springframework.security.saml.metadata.MetadataManager;
import org.springframework.security.saml.processor.SAMLProcessor;
import org.springframework.security.saml.storage.SAMLMessageStorage;
import org.springframework.security.saml.websso.WebSSOProfileConsumerImpl;
import org.springframework.util.Assert;

/**
 *
 */
public class VidyoCustomWebSSOProfileConsumerImpl extends WebSSOProfileConsumerImpl {

    public VidyoCustomWebSSOProfileConsumerImpl() {
    }

    public VidyoCustomWebSSOProfileConsumerImpl(SAMLProcessor processor, MetadataManager manager) {
        super(processor, manager);
    }

    @Override
    public String getProfileIdentifier() {
        return SAMLConstants.SAML2_WEBSSO_PROFILE_URI;
    }



    /**
     * The input context object must have set the properties related to the returned Response, which is validated
     * and in case no errors are found the SAMLCredential is returned.
     *
     *
     * @param context context including response object
     * @return SAMLCredential with information about user
     * @throws SAMLException       in case the response is invalid
     * @throws SAMLStatusException in case the response is returning status code other than success.
     * 								It will return the status code and message from the SAMLP
     * @throws org.opensaml.xml.security.SecurityException
     *                             in the signature on response can't be verified
     * @throws ValidationException in case the response structure is not conforming to the standard
     */
    public SAMLCredential processAuthenticationResponse(SAMLMessageContext context) throws SAMLException, org.opensaml.xml.security.SecurityException, ValidationException, DecryptionException {

        AuthnRequest request = null;
        SAMLObject message = context.getInboundSAMLMessage();

        // Verify type
        if (!(message instanceof Response)) {
            throw new SAMLException("Message is not of a Response object type");
        }
        Response response = (Response) message;

        // Verify status
        String statusCode = response.getStatus().getStatusCode().getValue();
        if (!StatusCode.SUCCESS_URI.equals(statusCode)) {
            StatusMessage statusMessage = response.getStatus().getStatusMessage();
            String statusMessageText = null;
            if (statusMessage != null) {
                statusMessageText = statusMessage.getMessage();
            }
            throw new SAMLStatusException(statusCode , statusMessageText);
        }

        // Verify signature of the response if present, unless already verified in binding
        if (response.getSignature() != null && !context.isInboundSAMLMessageAuthenticated()) {
            log.debug("Verifying Response signature");
            verifySignature(response.getSignature(), context.getPeerEntityId(), context.getLocalTrustEngine());
            context.setInboundSAMLMessageAuthenticated(true);
        }

        // Verify issue time
        DateTime time = response.getIssueInstant();
        if (!isDateTimeSkewValid(getResponseSkew(), time)) {
            throw new SAMLException("Response issue time is either too old or with date in the future, skew " + getResponseSkew() + ", time " + time);
        }

        // Reject unsolicited messages when disabled
        if (!context.getPeerExtendedMetadata().isSupportUnsolicitedResponse() && response.getInResponseTo() == null) {
            throw new SAMLException("Reception of Unsolicited Response messages (without InResponseToField) is disabled");
        }

        // Verify response to field if present, set request if correct
        SAMLMessageStorage messageStorage = context.getMessageStorage();
        if (messageStorage != null && response.getInResponseTo() != null) {
            XMLObject xmlObject = messageStorage.retrieveMessage(response.getInResponseTo());
            if (xmlObject == null) {
                throw new SAMLException("InResponseToField of the Response doesn't correspond to sent message " + response.getInResponseTo());
            } else if (xmlObject instanceof AuthnRequest) {
                request = (AuthnRequest) xmlObject;
            } else {
                throw new SAMLException("Sent request was of different type than the expected AuthnRequest " + response.getInResponseTo());
            }
        }

        // Verify that message was received at the expected endpoint
        verifyEndpoint(context.getLocalEntityEndpoint(), response.getDestination());

        // Verify endpoint requested in the original request
        if (request != null) {
            AssertionConsumerService assertionConsumerService = (AssertionConsumerService) context.getLocalEntityEndpoint();
            if (request.getAssertionConsumerServiceIndex() != null) {
                if (!request.getAssertionConsumerServiceIndex().equals(assertionConsumerService.getIndex())) {
                    log.info("Response was received at a different endpoint index than was requested");
                }
            } else {
                String requestedResponseURL = request.getAssertionConsumerServiceURL();
                String requestedBinding = request.getProtocolBinding();
                if (requestedResponseURL != null) {
                    String responseLocation;
                    if (assertionConsumerService.getResponseLocation() != null) {
                        responseLocation = assertionConsumerService.getResponseLocation();
                    } else {
                        responseLocation = assertionConsumerService.getLocation();
                    }
                    if (!requestedResponseURL.equals(responseLocation)) {
                        log.info("Response was received at a different endpoint URL {} than was requested {}", responseLocation, requestedResponseURL);
                    }
                }
                if (requestedBinding != null) {
                    if (!requestedBinding.equals(context.getInboundSAMLBinding())) {
                        log.info("Response was received using a different binding {} than was requested {}", context.getInboundSAMLBinding(), requestedBinding);
                    }
                }
            }
        }

        // Verify issuer
        if (response.getIssuer() != null) {
            log.debug("Verifying issuer of the Response");
            Issuer issuer = response.getIssuer();
            verifyIssuer(issuer, context);
        }

        Assertion subjectAssertion = null;
        List<Attribute> attributes = new ArrayList<Attribute>();
        List<Assertion> assertionList = response.getAssertions();

        // Decrypt assertions
        if (response.getEncryptedAssertions().size() > 0) {
            assertionList = new ArrayList<Assertion>(response.getAssertions().size() + response.getEncryptedAssertions().size());
            assertionList.addAll(response.getAssertions());
            List<EncryptedAssertion> encryptedAssertionList = response.getEncryptedAssertions();
            for (EncryptedAssertion ea : encryptedAssertionList) {
                try {
                    Assert.notNull(context.getLocalDecrypter(), "Can't decrypt Assertion, no decrypter is set in the context");
                    log.debug("Decrypting assertion");
                    Assertion decryptedAssertion = context.getLocalDecrypter().decrypt(ea);
                    assertionList.add(decryptedAssertion);
                } catch (DecryptionException e) {
                    log.debug("Decryption of received assertion failed, assertion will be skipped", e);
                }
            }
        }

        Exception lastError = null;

        // Find the assertion to be used for session creation and verify
        for (Assertion assertion : assertionList) {
            if (assertion.getAuthnStatements().size() > 0) {
                try {
                    // Verify that the assertion is valid
                    verifyAssertion(assertion, request, context);
                    subjectAssertion = assertion;
                    log.debug("Validation of authentication statement in assertion {} was successful", assertion.getID());
                    break;
                } catch (Exception e) {
                    log.debug("Validation of authentication statement in assertion failed, skipping", e);
                    lastError = e;
                }
            } else {
                log.debug("Assertion {} did not contain any authentication statements, skipping", assertion.getID());
            }
        }

        // Make sure that at least one assertion contains authentication statement and subject with bearer confirmation
        if (subjectAssertion == null) {
            throw new SAMLException("Response doesn't have any valid assertion which would pass subject validation", lastError);
        }

        // Process attributes from assertions
        for (Assertion assertion : assertionList) {
            if (assertion == subjectAssertion || isIncludeAllAttributes()) {
                for (AttributeStatement attStatement : assertion.getAttributeStatements()) {
                    for (Attribute att : attStatement.getAttributes()) {
                        log.debug("Including attribute {} from assertion {}", att.getName(), assertion.getID());
                        attributes.add(att);
                    }
                    for (EncryptedAttribute att : attStatement.getEncryptedAttributes()) {
                        Assert.notNull(context.getLocalDecrypter(), "Can't decrypt Attribute, no decrypter is set in the context");
                        Attribute decryptedAttribute = context.getLocalDecrypter().decrypt(att);
                        log.debug("Including decrypted attribute {} from assertion {}", decryptedAttribute.getName(), assertion.getID());
                        attributes.add(decryptedAttribute);
                    }
                }
            }
        }

        NameID nameId = (NameID) context.getSubjectNameIdentifier();
        if (nameId == null) {
            throw new SAMLException("NameID element must be present as part of the Subject in the Response message, please enable it in the IDP configuration");
        }

        // Populate custom data, if any
        Serializable additionalData = processAdditionalData(context);

        // Release extra DOM data which might get otherwise stored in session
        if (isReleaseDOM()) {
            subjectAssertion.releaseDOM();
            subjectAssertion.releaseChildrenDOM(true);
        }

        // Create the credential
        return new SAMLCredential(nameId, subjectAssertion, context.getPeerEntityMetadata().getEntityID(), context.getRelayState(), attributes, context.getLocalEntityId(), additionalData);

    }

 
}