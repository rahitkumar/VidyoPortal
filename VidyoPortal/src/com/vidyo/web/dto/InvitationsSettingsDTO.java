package com.vidyo.web.dto;

public class InvitationsSettingsDTO {

    private InvitationsSettingsDTO(){}

    public static class InvitationsDTOBuilder {

        private InvitationsSettingsDTO is = new InvitationsSettingsDTO();

        public InvitationsDTOBuilder withInvitationEmailContent(String s){
            is.invitationEmailContent =s;
            return this;
        }

        public InvitationsDTOBuilder withInvitationEmailContentHtml(String s){
            is.invitationEmailContentHtml = s;
            return this;
        }

        public InvitationsDTOBuilder withVoiceOnlyContent(String s){
            is.voiceOnlyContent = s;
            return this;
        }

        public InvitationsDTOBuilder withWebcastContent(String s){
            is.webcastContent = s;
            return this;
        }

        public InvitationsDTOBuilder withInvitationEmailSubject(String s){
            is.invitationEmailSubject = s;
            return this;
        }

        public InvitationsDTOBuilder withDialInNumbersGridChanged(boolean b){
            is.dialInNumbersGridChanged = b;
            return this;
        }

        public InvitationsDTOBuilder withDialInNumbers(String s){
            is.dialInNumbers = s;
            return this;
        }

        public InvitationsSettingsDTO build(){
            InvitationsSettingsDTO toReturn = new InvitationsSettingsDTO();
            toReturn.invitationEmailContent = is.invitationEmailContent;
            toReturn.invitationEmailContentHtml = is.invitationEmailContentHtml;
            toReturn.voiceOnlyContent = is.voiceOnlyContent;
            toReturn.webcastContent = is.webcastContent;
            toReturn.invitationEmailSubject = is.invitationEmailSubject;
            toReturn.dialInNumbers = is.dialInNumbers;
            toReturn.dialInNumbersGridChanged = is.dialInNumbersGridChanged;
            return toReturn;
        }

    }

    private String invitationEmailContent = "";
    private String invitationEmailContentHtml = "";
    private String voiceOnlyContent = "";
    private String webcastContent = "";
    private String invitationEmailSubject = "";
    private String dialInNumbers;
    private boolean dialInNumbersGridChanged = true;

    public String getDialInNumbers() {
        return dialInNumbers;
    }
    public boolean isDialInNumbersGridChanged() {
        return dialInNumbersGridChanged;
    }

    public String getInvitationEmailContent() {
        return invitationEmailContent;
    }

    public String getInvitationEmailContentHtml() {
        return invitationEmailContentHtml;
    }

    public String getVoiceOnlyContent() {
        return voiceOnlyContent;
    }

    public String getWebcastContent() {
        return webcastContent;
    }

    public String getInvitationEmailSubject() {
        return invitationEmailSubject;
    }
}
