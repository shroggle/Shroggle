#!/usr/bin/perl

#Congif part
$siteDir = "/var/www/html";
$archiveDir = "/var/www/ss";
$archivator = "/usr/bin/bzip2";
$archivarotParam = "";
$db_command = "/usr/local/bin/pg_dump";

print "\n\n\n";

$db_info_part = "iancordb21 -U pgsql ";
	
# Make directory Name
$date = `date +%Y-%m-%d`;
chop($date);
$date_file = `date +%Y_%m_%d.sql`;
chop($date_file);
print "Current Directory Name: $date\n";


# Create DB Dump
print "Creating DB-Dump ...... \n";
$db_cmd = "$db_command $db_info_part > $siteDir/iancordb21_dump_server_$date_file";
$db_dump = `$db_cmd`;
# Creating archive for db-dump
print "Create archive for db_dump\n";
$ar3 = `$archivator $siteDir/iancordb21_dump_server_$date_file $archivarotParam`;
print "\n";
 

#Make directory
print "Making archive directory\n";
$mkd1 = `mkdir $archiveDir/$date`;

#Moving files
print "Moving files\n";
`cp $siteDir/iancordb21_dump_server_$date_file.bz2 /home/public/DB-Archive/`; 
`mv $siteDir/iancordb21_dump_server_$date_file.bz2 $archiveDir/$date`;

