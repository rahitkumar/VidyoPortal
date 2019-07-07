#ifndef _UTILS_H_
#define _UTILS_H_

void Failed(const char *errmsg);
void Daemonize();
int file_exists(const char *fn);
bool lockfile(const char *fn, const int tmo=8);
bool unlockfile(const char *fn);
char * remove_cr(char *str);


#endif

