#!/usr/bin/perl

# What we gonna archive/backup here:
# 1. svn directory and settings
# 2. sudoer files
# 3. update scripts
# 4. cruise control settings
# 6. jira application
# 7. wiki application
# 8. nginx settings

# 8. Application database
# 9. CruiseControl project

#Congif part
$tmpDir = "/data/hosting/backups/tmp/";
$archiveStorageDir = "/data/hosting/backups/data/";
$archivatorPart1 = " tar -cf ";
$archivatorPart2 = " && bzip2 ";


sub HowdyEveryone {
   my($blockName, $blockDir) = @_;

print "\nCompressing $blockName ($blockDir) ... \n";

# Create archive
#print "\nFirst compress result\n";
# // tar -cf file.tar dir && bzip2 file.tar
$archiveName = "$tmpDir$blockName.$date_file.tar";
#print "$archivatorPart1 $archiveName $blockDir $archivatorPart2 $archiveName";
$ar1 = `$archivatorPart1 $archiveName $blockDir $archivatorPart2 $archiveName`;
#print "\nSecond compress result\n";
#$ar2 = `$archivator $blockDir/isql.txt $archivarotParam`;

# Create DB Dump
#print "Creating DB-Dump ...... \n";
#$db_cmd = "$db_command $db_info_part > $blockDir/iancordb9_dump_server_$date_file";
#$db_dump = `$db_cmd`;
# Creating archive for db-dump
#print "Create archive for db_dump\n";
#$ar3 = `$archivator $blockDir/iancordb9_dump_server_$date_file $archivarotParam`;
#print "\n";
 
#Moving files
print "Moving file $archiveName.bz2 to $archiveStorageDir$date ...\n";
`mv $archiveName.bz2 $archiveStorageDir$date`;

}

# Make dirrectory Name
$date = `date +%Y-%m-%d-%H-%M`;
chop($date);
$date_file = `date +%Y_%m_%d_%H_%M.sql`;
chop($date_file);
print "Current Backup Directory is $date\n\n";

#Make directory
print "Making archive directory ...\n";
$mkd1 = `mkdir $archiveStorageDir/$date`;


print &HowdyEveryone("update.com", "/data/scripts/update.com");
print &HowdyEveryone("update.net", "/data/scripts/update.net");
print &HowdyEveryone("update.wikalot", "/data/scripts/update.wikalot");

print &HowdyEveryone("svn", "/data/SVN");
print &HowdyEveryone("sudoers", "/usr/local/etc");

print &HowdyEveryone("cruise.tomcat.conf", "/opt/zones/env3/catalina/conf");
print &HowdyEveryone("cruise.webapp", "data1/CC/cruisecontrol/webapps");
print &HowdyEveryone("cruise.settings", "/data1/CC/cruisecontrol/*.xml");

print &HowdyEveryone("jira.dir", "/opt/zones/env4/catalina");
print &HowdyEveryone("wiki.dir", "/opt/zones/env5/catalina");
print &HowdyEveryone("nginx.conf", "/opt/nginx/conf");

print &HowdyEveryone("com.conf", "/data/hosting/www.shroggle.com/*.xml");
print &HowdyEveryone("com.data", "/data/hosting/www.shroggle.com/data");

print &HowdyEveryone("net.conf", "/data/hosting/www.shroggle.com/*.xml");
print &HowdyEveryone("net.data", "/data/hosting/www.shroggle.com/data");

print &HowdyEveryone("wikalot.conf", "/data/hosting/wikalot.com/*.xml");
print &HowdyEveryone("wikalot.data", "/data/hosting/wikalot.com/data");
