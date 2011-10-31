#!/usr/bin/perl

#Congif part
$siteDir = "/var/www/html";
$archiveDir = "/var/www/ss";
$archivator = "bzip2";
$archivarotParam = "";
$db_command = "pg_dump";

print "\n\n\n";

$db_info_part = "iancordb9 -U postgres ";
	
# Make dirrectory Name
$date = `date +%Y-%m-%d`;
chop($date);
$date_file = `date +%Y_%m_%d.sql`;
chop($date_file);
print "Current Directory Name: $date";

# Create archive
print "\nFirst compress result\n";
$ar1 = `$archivator $siteDir/ilog.txt $archivarotParam`;
print "\nSecond compress result\n";
$ar2 = `$archivator $siteDir/isql.txt $archivarotParam`;
print "\n";

# Create DB Dump
print "Creating DB-Dump ...... \n";
$db_cmd = "$db_command $db_info_part > $siteDir/iancordb9_dump_server_$date_file";
$db_dump = `$db_cmd`;
# Creating archive for db-dump
print "Create archive for db_dump\n";
$ar3 = `$archivator $siteDir/iancordb9_dump_server_$date_file $archivarotParam`;
print "\n";
 

#Make directory
print "Making archive directory\n";
$mkd1 = `mkdir $archiveDir/$date`;

#Moving files
print "Moving files\n";
`mv $siteDir/ilog.txt.bz2 $archiveDir/$date`;
`mv $siteDir/isql.txt.bz2 $archiveDir/$date`;
`mv $siteDir/iancordb9_dump_server_$date_file.bz2 $archiveDir/$date`;

