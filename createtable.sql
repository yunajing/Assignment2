CREATE TABLE `VIDEOINFO`(
`vid` int(10) DEFAULT 0,
`vname` varchar(100) NOT NULL DEFAULT '',
`count` int(4) DEFAULT 0,
`rating` int(1) DEFAULT 0,
`uploadtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
Primary key (`vid`)
);

Insert into VIDEOINFO(vID,vName,rating,count)VALUES(1,'cat01.mp4',5,1) 

