INSERT INTO user(id, username, password, role, membership_years, is_full_member, is_child) VALUES
    (1000,'admin','password','ADMIN',10,true,false),
    (1001,'a','p','ADMIN',10,true,false),
    (1002,'u','p','USER',10,true,false),
    (1003,'ahsoka tano','padawan','USER',16,false,true),
    (1004,'Plo Koon','104th Battalion','ADMIN',385,true,false),
    (1005,'Mace Windu','187th Legion','USER',72,true,false),
    (1006,'Ki-Adi-Mundi','Cerea','ADMIN',65,true,false),
    (1007,'Captain Rex','CT-7567','USER',16,false,false),
    (1008,'Commander Wolffe','CC-3636','USER',16,false,true),
    (1009,'yoda','dordonot','ADMIN',900,true,false);

INSERT INTO media(id, title, mediatext, mediatype, is_child_suitable, is_loanable, is_reserved, isviewable) VALUES
    (1000,'Blade Squadron: Part 1','#149, April 2014','Holocron',true,false,false,true),
    (1001,'Blade Squadron: Part 2','#150, April 2014','Holocron',false,true,false,true),
    (1002,'One Thousand Levels Down','#151, July 2014','Chronicles',true,true,false,true),
    (1003,'The End of History','#154, December 2014','Scroll',true,true,false,true),
    (1004,'Bottleneck','John Jackson Miller','Holocron',false,true,false,true),
    (1005,'The Levers of Power','Jason Fry','Celestial Chart',false,true,false,true),
    (1006,'Raymus','Gary Whitta','Scroll',true,true,false,true),
    (1007,'The Sith of Datawork','Ken Liu','Holocron',true,true,false,true),
    (1008,'The Red One','Rae Carson','Artifact',true,true,false,true),
    (1009,'Beru Whitesun Lars',' Meg Cabot','Holocron',false,true,false,true),
    (1010,'We Don''t Serve Their Kind Here','Chuck Wendig','Cassette Tape',false,true,false,true),
    (1011,'Mercy Mission','Melissa Scott','Cassette Tape',false,true,false,true);
