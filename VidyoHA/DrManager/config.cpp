#include <string.h>
#include "config.h"
#include "mydefs.h"


//! \brief Initialize a singleton CConfig object.
CConfig *CConfig::Initialize()
{
   if ( !pInstance ) {
      pInstance = new CConfig();
   }

   return pInstance;
}

//! \brief Get the instance of CConfig object
CConfig *CConfig::GetInstance()
{
   return pInstance;
}

//! \brief Read the content of the config file and loads them into the memory.
//! fn - filename of the configuration file containing a name value format.
bool CConfig::Read(const char *fn)
{
   nv.clear();

   if ( (fp = fopen(fn, "r")) == NULL ) {
      return false;
   } 

   char buf[1024];
   char *saveptr;
   char *ptr;
   string name;
   string value;
   while(1) {

      memset(buf, 0, sizeof(buf));
      if (!fgets(buf, SIZE(buf), fp) )
         break;
      if ( Comment(buf) ) 
         continue;

      if ( ( ptr = strtok_r(buf, "=", &saveptr) ) ) {
         name = ptr;
         if ( ( ptr = strtok_r(NULL, "\n", &saveptr) ) ) {
            value = ptr;
            //printf("%s=%s\n", name.c_str(), value.c_str());
            nv[name]=value; 
         }
      }
   }

   printf("size of map: %d\n", nv.size());
   fclose(fp);
   return true;
}

//! \brief Returns true if the first character of the str if '#'.
bool CConfig::Comment(const char *str)
{
   bool comment = false;
   const char *ptr=str;
   while( *ptr ) {
      if ( *ptr == '#' ) {
         comment = true; 
         break;
      }
      if ( *ptr != ' ' )
         break; 
      ptr++;
   }
   return comment;
}

//! \brief Returns the value of the field in a configuration file that was loaded into the memory.
//! str - A valid field name on the configuration file.
//! returns NULL if str is not a valid field on the configuration file.
const char *CConfig::NamVal(const char *str)
{
   string name(str);
   map<string,string>::iterator it;
   if ( (it = nv.find(name)) == nv.end() )
      return NULL;
   else
      return it->second.c_str();
}

//! \brief Dump the contents of the configuration file into the memory.
void CConfig::Dump()
{
   map<string,string>::iterator it;

   for ( it = nv.begin(); it != nv.end(); it++ ) {
      printf("%s=%s\n", it->first.c_str(), it->second.c_str());
   }
}

//! \brief  Read the configuration and returns the value of the field name.
//! fn - filename of the configuration file in a name value pair format.
//! pname - field name inside the configuration file.
char *CConfig::ReadValue(const char *fn, const char *fieldname)
{
   nv.clear();

   if ( (fp = fopen(fn, "r")) == NULL ) {
      return NULL;
   } 

   char buf[1024];
   char *saveptr;
   char *ptr=NULL;
   string name;
   while(1) {

      memset(buf, 0, sizeof(buf));
      if (!fgets(buf, SIZE(buf), fp) )
         break;
      if ( Comment(buf) ) 
         continue;

      if ( ( ptr = strtok_r(buf, "=", &saveptr) ) ) {
         name = ptr;
         if ( ( ptr = strtok_r(NULL, "\n", &saveptr) ) ) {
            if ( name.compare( fieldname ) == 0 ) { 
               break;
            }
         }
      }
   }

   fclose(fp);
   return ptr;
}
