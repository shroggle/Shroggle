-- 2010-11-01 Tolik
alter table draftPageSettings add column updated datetime not null;
update draftPageSettings set updated = now() where updated is null;