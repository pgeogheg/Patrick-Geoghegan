CREATE TABLE channel (
		c_name VARCHAR(50) NOT NULL, 
		joined DATE NOT NULL, 
		sub_count NUMBER NOT NULL, 
		view_count NUMBER NOT NULL, 
		genre VARCHAR(225) NOT NULL, 
		PRIMARY KEY(c_name)
);

INSERT INTO channel VALUES ('PewDiePie',TO_DATE('29/04/2010','dd/mm/yyyy'), 3, 19014393,'Games');
INSERT INTO channel VALUES ('smosh', TO_DATE('19/11/2005', 'dd/mm/yyyy'), 4, 7720313, 'Shows'	);
INSERT INTO channel VALUES ('Fine Brothers Entertainment',TO_DATE('04/06/2007', 'dd/mm/yyyy'), 2, 2310015,'Film');
INSERT INTO channel VALUES ('Spinnin Records',TO_DATE('12/07/2007', 'dd/mm/yyyy'), 2,590448,'Music');
INSERT INTO channel VALUES ('CollegeHumor',TO_DATE('09/10/2006', 'dd/mm/yyyy'), 3, 2134746,'Shows');

CREATE TABLE video (
		v_id NUMBER NOT NULL, 
		v_name VARCHAR(50) NOT NULL, 
		views NUMBER NOT NULL, 
		likes NUMBER NOT NULL, 
		dislikes NUMBER NOT NULL, 
		published VARCHAR(50) NOT NULL,
		status VARCHAR(8) NOT NULL,
		video_url VARCHAR(22) NOT NULL,
		PRIMARY KEY(v_id), 
		FOREIGN KEY(published) REFERENCES channel(c_name),
		CONSTRAINT status_name CHECK (status IN ('PUBLIC', 'PRIVATE', 'UNLISTED')),
		CONSTRAINT unique_v_url UNIQUE (video_url)
);

INSERT INTO video VALUES (0,'10 BILLION MONTAGE!',13463373, 289978, 4119, 'PewDiePie','PUBLIC', 'youtube.com/v/aushfbye');
INSERT INTO video VALUES (1,'Roller Coaster... of Death!!1!',2416643,127395,1405,'PewDiePie','PUBLIC','youtube.com/v/apdkwicn');
INSERT INTO video VALUES (2,'SWEDEN SIMULATOR',3134377,158991,1957, 'PewDiePie','PUBLIC','youtube.com/v/mzidhue');
INSERT INTO video VALUES (3,'EVERY JEDI EVER',1969255,70539,2124,'smosh','PUBLIC','youtube.com/v/plandhyt');
INSERT INTO video VALUES (4,'THE BAD PARTS OF HEAVEN',3142794,71023,5398,'smosh','PUBLIC','youtube.com/v/hfudisua');
INSERT INTO video VALUES (5,'MOVIE TRANSLATION FAILS',2608264,76706,4408,'smosh','PUBLIC','youtube.com/v/ppalddsa');
INSERT INTO video VALUES (6,'STAR WARS IN 1 TAKE IN 6 MINUTES',523437,13746,866,'Fine Brothers Entertainment','PUBLIC','youtube.com/v/ascsfsuh');
INSERT INTO video VALUES (7,'PARENTS REACT TO SELFIE STICKS',1081202,29312,421,'Fine Brothers Entertainment','PUBLIC','youtube.com/v/lapduhss');
INSERT INTO video VALUES (8,'50 YOUTUBE SPOILERS IN 4 MINUTES (OCTOBER 2015)',715376,13668,339,'Fine Brothers Entertainment','PUBLIC','youtube.com/v/gahsgtef');
INSERT INTO video VALUES (9,'HI-LO - Ooh La La (Official Music Video)',118968,5554,841,'Spinnin Records','PUBLIC','youtube.com/v/fhdjdd');
INSERT INTO video VALUES (10,'LVNDSCAPE ft. Joel Baker - Speeches (Lyric Video)',269840,7962,261,'Spinnin Records','PUBLIC','youube.com/v/ploanvcx');
INSERT INTO video VALUES (11,'Blasterjaxx - Heartbreak [Trailer]',201640,7391,205,'Spinnin Records','PUBLIC','youtube.com/v/kifhdgyr');
INSERT INTO video VALUES (12,'Everyone Is Waiting To Talk About Themselves',440085,18581,471,'CollegeHumor','PUBLIC','youtube.com/v/atsgdvye');
INSERT INTO video VALUES (13,'If People Acted Like They Do In Cars',747639,19164,3411,'CollegeHumor','PUBLIC','youtube.com/v/tdufnvhr');
INSERT INTO video VALUES (14,'Why The Electoral College Ruins Democracy',947022,32471,644,'CollegeHumor','PUBLIC','youtube.com/v/ufhdydgd');
INSERT INTO video VALUES (15,'Unpublished',0,0,0,'PewDiePie','PRIVATE','youtube.com/v/yshdbufi');
INSERT INTO video VALUES (16,'Unpublished',0,0,0,'PewDiePie','PRIVATE','youtube.com/v/jkshdbfi');
INSERT INTO video VALUES (17,'Unpublished',0,0,0,'PewDiePie','PRIVATE','youtube.com/v/quwyetsf');

CREATE TABLE comments(
		c_id NUMBER NOT NULL, 
		sender VARCHAR(50) NOT NULL, 
		comment_text VARCHAR(255) NOT NULL, 
		thumbs NUMBER NOT NULL, 
		video_id NUMBER NOT NULL, 
		PRIMARY KEY(c_id),
		FOREIGN KEY(sender) REFERENCES channel(c_name), 
		FOREIGN KEY(video_id) REFERENCES video(v_id)
);

INSERT INTO comments VALUES (0,'smosh','Because thats how we got George W. Bush.﻿',382,14);
INSERT INTO comments VALUES (1,'smosh','This is my fave Pewds vid﻿',113,0);
INSERT INTO comments VALUES (2,'smosh','In total on every video﻿',0,0);
INSERT INTO comments VALUES (3,'PewDiePie','Wait there are only 7.8 billion people on earth',5,0);
INSERT INTO comments VALUES (4,'PewDiePie','Isnt that Corys mom... xD﻿',6,12);

CREATE TABLE playlist(
		p_id NUMBER NOT NULL, 
		p_name VARCHAR(255) NOT NULL, 
		created DATE NOT NULL, 
		description VARCHAR(50) NOT NULL, 
		private VARCHAR(5) NOT NULL, 
		created_by VARCHAR(50) NOT NULL,
		playlist_url VARCHAR(22) NOT NULL,
		PRIMARY KEY(p_id),
		FOREIGN KEY(created_by) REFERENCES channel(c_name),
		CONSTRAINT boolean CHECK (private IN ('TRUE', 'FALSE')),
		CONSTRAINT unique_p_url UNIQUE(playlist_url)
);

INSERT INTO playlist VALUES (0,'November 2015 Playlist',TO_DATE('01/11/2015', 'dd/mm/yyyy'), 'Videos uploaded November 2015','FALSE','PewDiePie','youtube.com/p/hdbystfg');
INSERT INTO playlist VALUES (1,'Prank Videos',TO_DATE('13/02/2006', 'dd/mm/yyyy'),'Videos where we prank people','FALSE','smosh','youtube.com/p/ahysgydu');
INSERT INTO playlist VALUES (2,'Liked Videos',TO_DATE('19/11/2005', 'dd/mm/yyyy'),'Personal favourites','TRUE','smosh','youtube.com/p/jdufhgyb');
INSERT INTO playlist VALUES (3,'Dubstep',TO_DATE('22/07/2010', 'dd/mm/yyyy'),'All songs are dubstep','FALSE','Spinnin Records','youtube.com/p/qsvhdnig');
INSERT INTO playlist VALUES (4,'Unpublished',TO_DATE('04/06/2010', 'dd/mm/yyyy'),'Videos to be released','TRUE','PewDiePie','youtube.com/p/llkfmspo');

CREATE TABLE subscribed (
		channel_name VARCHAR(50) NOT NULL, 
		subscribed_to VARCHAR(50) NOT NULL,
		CONSTRAINT subscribed_key PRIMARY KEY(channel_name, subscribed_to),
		CONSTRAINT notEquality CHECK (channel_name != subscribed_to)
);

INSERT INTO subscribed VALUES ('PewDiePie','smosh');
INSERT INTO subscribed VALUES ('PewDiePie','CollegeHumor');
INSERT INTO subscribed VALUES ('smosh','Fine Brothers Entertainment');
INSERT INTO subscribed VALUES ('smosh','CollegeHumor');
INSERT INTO subscribed VALUES ('smosh','Spinnin Records');
INSERT INTO subscribed VALUES ('Fine Brothers Entertainment','PewDiePie');
INSERT INTO subscribed VALUES ('Fine Brothers Entertainment','smosh');
INSERT INTO subscribed VALUES ('Spinnin Records','PewDiePie');
INSERT INTO subscribed VALUES ('Spinnin Records','smosh');
INSERT INTO subscribed VALUES ('Spinnin Records','CollegeHumor');
INSERT INTO subscribed VALUES ('CollegeHumor','PewDiePie');
INSERT INTO subscribed VALUES ('CollegeHumor','smosh');
INSERT INTO subscribed VALUES ('CollegeHumor','Fine Brothers Entertainment');
INSERT INTO subscribed VALUES ('CollegeHumor','Spinnin Records');

CREATE TABLE videolist	(
		id_video NUMBER NOT NULL, 
		id_playlist NUMBER NOT NULL,
		CONSTRAINT videoList_key PRIMARY KEY(id_video, id_playlist)
);

INSERT INTO videolist VALUES (0,0);
INSERT INTO videolist VALUES (1,0);
INSERT INTO videolist VALUES (2,0);
INSERT INTO videolist VALUES (3,1);
INSERT INTO videolist VALUES (4,1);
INSERT INTO videolist VALUES (0,2);
INSERT INTO videolist VALUES (2,2);
INSERT INTO videolist VALUES (12,2);
INSERT INTO videolist VALUES (13,2);
INSERT INTO videolist VALUES (14,2);
INSERT INTO videolist VALUES (9,3);
INSERT INTO videolist VALUES (10,3);
INSERT INTO videolist VALUES (11,3);
INSERT INTO videolist VALUES (15,4);
INSERT INTO videolist VALUES (16,4);
INSERT INTO videolist VALUES (17,4);

--SELECT * FROM channel;
--SELECT * FROM video;
--SELECT * FROM comments;
--SELECT * FROM playlist;
--SELECT * FROM subscribed;
--SELECT * FROM videolist;

CREATE TRIGGER DeleteVideoComments
	BEFORE DELETE ON video
	FOR EACH ROW
DECLARE
	video_name_id NUMBER;
BEGIN
	video_name_id := :OLD.v_id;
	DELETE FROM comments
	WHERE video_name_id = video_id;
END DeleteVideoComments;
.
RUN;

show errors trigger DeleteVideoComments

CREATE TRIGGER DecreaseChannelViews
	BEFORE DELETE ON video
	FOR EACH ROW
DECLARE
	old_video_views NUMBER;
	old_channel_id VARCHAR(50);
BEGIN
	old_video_views := :OLD.views;
	old_channel_id := :OLD.published;
	UPDATE channel
	SET view_count = view_count - old_video_views
	WHERE c_name = old_channel_id;
END DecreaseChannelViews;
.
RUN;

show errors trigger DecreaseChannelViews

CREATE TRIGGER IncreaseChannelViews
	AFTER INSERT ON video
	FOR EACH ROW
DECLARE
	new_views NUMBER;
	new_video_owner VARCHAR(50);
BEGIN
	new_views := :NEW.views;
	new_video_owner := :NEW.published;
	UPDATE channel
	SET view_count = view_count + new_views
	WHERE c_name = new_video_owner;
END IncreaseChannelViews;
.
RUN;

show errors trigger IncreaseChannelViews

--DELETE FROM video WHERE v_id = 0;

--SELECT * FROM video WHERE v_id = 0;
--SELECT * FROM comments WHERE video_id = 0;

--SELECT * FROM channel WHERE c_name = 'PewDiePie';
--INSERT INTO video VALUES (99,'IM LEAVING YOUTUBE!',72639516, 789, 536422, 'PewDiePie','PUBLIC','youtube.com/v/aishbdyf');
--SELECT * FROM channel WHERE c_name = 'PewDiePie';
--DELETE FROM video WHERE v_id = 99;
--SELECT * FROM channel WHERE c_name = 'PewDiePie';

CREATE VIEW only_public_playlist AS
	SELECT c_name, p_name
	FROM channel, playlist
	WHERE private = 'FALSE' AND c_name = playlist.created_by;

--SELECT * FROM only_public_playlist;

CREATE VIEW only_public_videos AS
	SELECT c_name, v_name
	FROM channel, video
	WHERE status = 'PUBLIC' AND c_name = video.published;

SELECT * FROM only_public_videos;

CREATE VIEW videos_in_playlist AS
	SELECT p_name, v_name
	FROM playlist, video, videolist
	WHERE p_id = id_playlist AND v_id = id_video
	ORDER BY p_name ASC, v_name ASC;

--SELECT * FROM videos_in_playlist;
SELECT channel.c_name, channel.genre, video.v_name, video.views, video.likes
FROM channel
INNER JOIN video
ON channel.c_name = video.published; 

DROP TABLE channel cascade constraints;
DROP TABLE video cascade constraints;
DROP TABLE comments cascade constraints;
DROP TABLE playlist cascade constraints;
DROP TABLE subscribed cascade constraints;
DROP TABLE videolist cascade constraints;
DROP VIEW only_public_playlist;
DROP VIEW videos_in_playlist;
DROP VIEW only_public_videos;
