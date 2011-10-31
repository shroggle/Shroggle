-- 2010-10-26 Tolik
alter table tellFriends add column sendEmails bit(1) not null default 1;
alter table workTellFriends add column sendEmails bit(1) not null default 1;

update tellFriends set sendEmails = 1 where sendEmails is null;
update workTellFriends set sendEmails = 1 where sendEmails is null;