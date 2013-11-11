ALTER TABLE `VIDEOINFO` DROP `uploadtime`;
ALTER TABLE `VIDEOINFO` ADD `uploadtime` timestamp not null default current_timestamp;