CREATE DATABASE web_toeic

drop table nguoi_dung
CREATE TABLE nguoi_dung(
  email varchar(255)  PRIMARY KEY NOT NULL,
  dia_chi varchar(255) DEFAULT NULL,
  ho_ten varchar(55)  DEFAULT NULL,
  matkhau varchar(255)  DEFAULT NULL,
  so_dien_thoai varchar(11) DEFAULT NULL,
  vai_tro int DEFAULT NULL,
)
insert into nguoi_dung(email,matkhau) values('h@gmail.com','1')
CREATE TABLE ket_qua (
  cauhoibaithithuid int,
  email varchar(255),
  ngaylambai date,
  dungsai bit,
  part int,
  primary key(cauhoibaithithuid,email,ngaylambai),
 CONSTRAINT fk_cauhoibaithithuid_ketqua FOREIGN KEY (cauhoibaithithuid) REFERENCES cau_hoi_bai_thi_thu (cauhoibaithithuid) ON DELETE CASCADE,
 CONSTRAINT fk_email_nguoidung FOREIGN KEY (email) REFERENCES nguoi_dung (email) ON DELETE CASCADE,
)

CREATE TABLE cau_hoi_bai_thi_thu (
  cauhoibaithithuid int NOT NULL IDENTITY(1,1) PRIMARY KEY,
  audiomp3 varchar(255) DEFAULT NULL,
  image_test varchar(255) DEFAULT NULL,
  part int DEFAULT NULL,
  question varchar(255) DEFAULT NULL,
  diagram varchar(max)
)

create table lua_chon_dap_an(
  cauhoibaithithuid int ,
  choice varchar(1),
  content varchar(255),
  result varchar(255),
  primary key(cauhoibaithithuid,choice),
  CONSTRAINT fk_baithithuid_luachon FOREIGN KEY (cauhoibaithithuid) REFERENCES cau_hoi_bai_thi_thu (cauhoibaithithuid) ON DELETE CASCADE
)
delete  from ket_qua
go
Create procedure check_login
(
    @email varchar(255),
	@matkhau varchar(255)
)
	As 
	begin
		select * from nguoi_dung where email=@email and matkhau=@matkhau
	end
	go
	
	Create procedure get_question
(
    @part tinyint,
	@email varchar(30)
)    
	As 
	begin
	Declare @num int
	Declare @idcauhoi int
	set @num = (select COUNT(*) from ket_qua where email = @email and part=@part and ngaylambai =CAST( GETDATE() AS Date ))
     SELECT    TOP (1) @idcauhoi= cauhoibaithithuid
    FROM      cau_hoi_bai_thi_thu
    WHERE    (part = @part) and cauhoibaithithuid not in (select cauhoibaithithuid from ket_qua where email = @email and ngaylambai =CAST( GETDATE() AS Date ) )  
    ORDER BY NEWID()
    
    SELECT     cauhoibaithithuid, question, part,@num,audiomp3,image_test
    FROM      cau_hoi_bai_thi_thu
    WHERE     cauhoibaithithuid= @idcauhoi
    if @num < 5
    begin
    insert into ket_qua(cauhoibaithithuid,email,ngaylambai,part) values (@idcauhoi,@email,CAST( GETDATE() AS Date),@part)
    end
	end
delete from ket_qua
select * from ket_qua
go
Create procedure get_choice
(
	@idQuestion int
)
	As
	begin
	select * from lua_chon_dap_an where cauhoibaithithuid = @idQuestion
	end
	Create procedure get_choice_vocab
(
	@idQuestion int
)
	As
	begin
	select * from lua_chon_dap_an where cauhoibaithithuid = @idQuestion
	order by newID()
	end
	go
Create procedure insert_ketqua
(
	@cauhoibaithithuid int,
	@email varchar(50),
	@dungsai bit
)
	As
	begin
	update ket_qua set dungsai = @dungsai where cauhoibaithithuid = @cauhoibaithithuid and email = @email and ngaylambai = CAST(GetDate() as DATE)
	end
	go
DBCC CHECKIDENT ('[cau_hoi_bai_thi_thu]', RESEED, 137);
GO

delete from ket_qua
select * from cau_hoi_bai_thi_thu
select *from ket_qua 
go
Create FUNCTION [dbo].[Diem_get]
(
	-- Add the parameters for the function here
@user varchar(20),
@part tinyint
)
RETURNS int
AS
BEGIN
	-- Declare the return variable here
	DECLARE @re int

	-- Add the T-SQL statements to compute the return value here
SELECT   @re=  COUNT(*) 
FROM         dbo.ket_qua
WHERE     (dungsai = 1) AND (email = @user) AND (part = @part) and  (ngaylambai = CAST(GETDATE() AS date))

if @re is NULL
Set @re=0
	-- Return the result of the function
	RETURN @re

END
go
Create FUNCTION [dbo].[Diem_thang_get]
(
	-- Add the parameters for the function here
@user varchar(20),
@part tinyint
)
RETURNS int
AS
BEGIN
	-- Declare the return variable here
	DECLARE @re int

	-- Add the T-SQL statements to compute the return value here
SELECT     @re =COUNT(*) 
FROM         dbo.ket_qua
WHERE     (dungsai = 1) AND (email =@user) AND (part = @part) AND (MONTH(ngaylambai) = MONTH(GETDATE()))and (Year(ngaylambai) = Year(GETDATE()))

if @re is NULL
Set @re=0
	-- Return the result of the function
	RETURN @re

END
select  part,COUNT(email)as day
from ket_qua
where email='h@gmail.com' and dungsai = 1 and  ngaylambai = CAST(GetDate() as DATE) group by part
select * from ket_qua
go 
Create PROCEDURE [dbo].[temp]
	-- Add the parameters for the stored procedure here
	@user varchar(20)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
declare @temp Table (part int, diem tinyint, diem_thang tinyint,mail varchar(20))
declare @i int
Set @i=1
While @i<=8
begin
insert @temp Select @i,dbo.Diem_get(email,@i),dbo.Diem_thang_get(email,@i), email from nguoi_dung where email = @user

Set @i=@i+1
end
select * from @temp
end
<<<<<<< HEAD
DECLARE @count INT;
SET @count = 51;
    
WHILE @count<= 80
BEGIN
   insert cau_hoi_bai_thi_thu(audiomp3,image_test,part) values ('image_sound\'+Cast(@count as varchar)+'.mp3','image_sound\'+cast(@count as varchar)+'.png',1)
   SET @count = @count + 1;
END;
select * from ket_qua
delete  from ket_qua
go
Create procedure [dbo].[get_multiple_question]
(
    @part tinyint,
	@email varchar(30)
)    
	As 
	begin
	Declare @num int
	Declare @idcauhoi int
	Declare @image_sound varchar(100)
	declare @temp Table (cauhoibaithithuid int, question varchar(100), part tinyint,num tinyint,audiomp3 varchar(100), image_test varchar(100) )
	set @num = (select COUNT(*) from ket_qua where email = @email and part=@part and ngaylambai =CAST( GETDATE() AS Date ))
	
	if @part = 3 or @part = 4
	begin
	select top (1) @image_sound = audiomp3
	from cau_hoi_bai_thi_thu
	where (part = @part) and (audiomp3 not in  (select cau_hoi_bai_thi_thu.audiomp3 from ket_qua right join cau_hoi_bai_thi_thu on ket_qua.cauhoibaithithuid = cau_hoi_bai_thi_thu.cauhoibaithithuid where email = @email and ngaylambai =CAST( GETDATE() AS Date )) or audiomp3 in (select cau_hoi_bai_thi_thu.audiomp3 from ket_qua right join cau_hoi_bai_thi_thu on ket_qua.cauhoibaithithuid = cau_hoi_bai_thi_thu.cauhoibaithithuid where email = @email and dungsai =0))
    ORDER BY NEWID()    

      while @num < 3
    begin
     SELECT    TOP (1) @idcauhoi= cauhoibaithithuid
    FROM      cau_hoi_bai_thi_thu
    WHERE    (part = @part) and audiomp3 = @image_sound and cauhoibaithithuid not in (select cauhoibaithithuid from ket_qua where email = @email and ngaylambai =CAST( GETDATE() AS Date ) )  
     ORDER BY cauhoibaithithuid
    
    insert @temp Select cauhoibaithithuid, question, part,@num,audiomp3,image_test FROM cau_hoi_bai_thi_thu WHERE cauhoibaithithuid= @idcauhoi 
    
    SELECT     cauhoibaithithuid, question, part,@num,audiomp3,image_test 
    FROM      cau_hoi_bai_thi_thu
    WHERE     cauhoibaithithuid= @idcauhoi  
    insert into ket_qua(cauhoibaithithuid,email,ngaylambai,part) values (@idcauhoi,@email,CAST( GETDATE() AS Date),@part)
    
    set @num = (select COUNT(*) from ket_qua where email = @email and part=@part and ngaylambai =CAST( GETDATE() AS Date ))
	end
	end
	select * from @temp
	end
	DECLARE @count INT;
SET @count = 81;
    
WHILE @count<= 105
BEGIN
   insert cau_hoi_bai_thi_thu(audiomp3,part) values ('image_sound\'+Cast(@count as varchar)+'.mp3',2)
   SET @count = @count + 1;
END;
ALTER procedure [dbo].[get_multiple_question]
(
    @part tinyint,
	@email varchar(30)
)    
	As 
	begin
	Declare @num int
	Declare @idcauhoi int
	Declare @image_sound varchar(100)
	Declare @countQuestion int
	declare @temp Table (cauhoibaithithuid int, question varchar(255), part tinyint,num tinyint,audiomp3 varchar(100), image_test varchar(100))
	set @num = (select COUNT(*) from ket_qua where email = @email and part=@part and ngaylambai =CAST( GETDATE() AS Date ))
	if @num=0
	begin
	if @part = 3 or @part = 4
	begin
	
	select top (1) @image_sound = audiomp3
	from cau_hoi_bai_thi_thu
	where (part = @part) and (audiomp3 not in  (select cau_hoi_bai_thi_thu.audiomp3 from ket_qua right join cau_hoi_bai_thi_thu on ket_qua.cauhoibaithithuid = cau_hoi_bai_thi_thu.cauhoibaithithuid where email = @email and ngaylambai =CAST( GETDATE() AS Date )) or audiomp3 in (select cau_hoi_bai_thi_thu.audiomp3 from ket_qua right join cau_hoi_bai_thi_thu on ket_qua.cauhoibaithithuid = cau_hoi_bai_thi_thu.cauhoibaithithuid where email = @email and dungsai =0))
    ORDER BY NEWID()    
       select @countQuestion = Count(cauhoibaithithuid)
     from cau_hoi_bai_thi_thu
     where audiomp3=@image_sound   
      while @num < @countQuestion
    begin
     SELECT    TOP (1) @idcauhoi= cauhoibaithithuid
    FROM      cau_hoi_bai_thi_thu
    WHERE    (part = @part) and audiomp3 = @image_sound and cauhoibaithithuid not in (select cauhoibaithithuid from ket_qua where email = @email and ngaylambai =CAST( GETDATE() AS Date ) )  
     ORDER BY cauhoibaithithuid
    
    insert @temp Select cauhoibaithithuid, question, part,@num,audiomp3,image_test FROM cau_hoi_bai_thi_thu WHERE cauhoibaithithuid= @idcauhoi 
    
    SELECT     cauhoibaithithuid, question, part,@num,audiomp3,image_test,@countQuestion 
    FROM      cau_hoi_bai_thi_thu
    WHERE     cauhoibaithithuid= @idcauhoi  
    insert into ket_qua(cauhoibaithithuid,email,ngaylambai,part) values (@idcauhoi,@email,CAST( GETDATE() AS Date),@part)
    
    set @num = (select COUNT(*) from ket_qua where email = @email and part=@part and ngaylambai =CAST( GETDATE() AS Date ))
	end
	end
	if @part = 6 
	begin
	select top (1) @image_sound = image_test
	from cau_hoi_bai_thi_thu
	where (part = @part) and (image_test not in  (select cau_hoi_bai_thi_thu.image_test from ket_qua right join cau_hoi_bai_thi_thu on ket_qua.cauhoibaithithuid = cau_hoi_bai_thi_thu.cauhoibaithithuid where email = @email and ngaylambai =CAST( GETDATE() AS Date )) or image_test in (select cau_hoi_bai_thi_thu.image_test from ket_qua right join cau_hoi_bai_thi_thu on ket_qua.cauhoibaithithuid = cau_hoi_bai_thi_thu.cauhoibaithithuid where email = @email and dungsai =0))
    ORDER BY NEWID()    
       select @countQuestion = Count(cauhoibaithithuid)
     from cau_hoi_bai_thi_thu
     where image_test=@image_sound   
      while @num < @countQuestion
    begin
     SELECT    TOP (1) @idcauhoi= cauhoibaithithuid
    FROM      cau_hoi_bai_thi_thu
    WHERE    (part = @part) and image_test = @image_sound and cauhoibaithithuid not in (select cauhoibaithithuid from ket_qua where email = @email and ngaylambai =CAST( GETDATE() AS Date ) )  
     ORDER BY cauhoibaithithuid
    
    insert @temp Select cauhoibaithithuid, question, part,@num,audiomp3,image_test FROM cau_hoi_bai_thi_thu WHERE cauhoibaithithuid= @idcauhoi 
    
    SELECT     cauhoibaithithuid, question, part,@num,audiomp3,image_test,@countQuestion 
    FROM      cau_hoi_bai_thi_thu
    WHERE     cauhoibaithithuid= @idcauhoi  
    insert into ket_qua(cauhoibaithithuid,email,ngaylambai,part) values (@idcauhoi,@email,CAST( GETDATE() AS Date),@part)
    
    set @num = (select COUNT(*) from ket_qua where email = @email and part=@part and ngaylambai =CAST( GETDATE() AS Date ))
	end
	end
	if @part = 7 
	begin
	select top (1) @image_sound = image_test
	from cau_hoi_bai_thi_thu
	where (part = @part) and (image_test not in  (select cau_hoi_bai_thi_thu.image_test from ket_qua right join cau_hoi_bai_thi_thu on ket_qua.cauhoibaithithuid = cau_hoi_bai_thi_thu.cauhoibaithithuid where email = @email and ngaylambai =CAST( GETDATE() AS Date )) or image_test in (select cau_hoi_bai_thi_thu.image_test from ket_qua right join cau_hoi_bai_thi_thu on ket_qua.cauhoibaithithuid = cau_hoi_bai_thi_thu.cauhoibaithithuid where email = @email and dungsai =0))
    ORDER BY NEWID()
     select @countQuestion = Count(cauhoibaithithuid)
     from cau_hoi_bai_thi_thu
     where image_test=@image_sound   
      while @num < @countQuestion
    begin
     SELECT    TOP (1) @idcauhoi= cauhoibaithithuid
    FROM      cau_hoi_bai_thi_thu
    WHERE    (part = @part) and image_test = @image_sound and cauhoibaithithuid not in (select cauhoibaithithuid from ket_qua where email = @email and ngaylambai =CAST( GETDATE() AS Date ) )  
     ORDER BY cauhoibaithithuid
    
    insert @temp Select cauhoibaithithuid, question, part,@num,audiomp3,image_test FROM cau_hoi_bai_thi_thu WHERE cauhoibaithithuid= @idcauhoi 
    
    SELECT     cauhoibaithithuid, question, part,@num,audiomp3,image_test,@countQuestion 
    FROM      cau_hoi_bai_thi_thu
    WHERE     cauhoibaithithuid= @idcauhoi  
    insert into ket_qua(cauhoibaithithuid,email,ngaylambai,part) values (@idcauhoi,@email,CAST( GETDATE() AS Date),@part)
    
    set @num = (select COUNT(*) from ket_qua where email = @email and part=@part and ngaylambai =CAST( GETDATE() AS Date ))
	end
	end
	end
	select * from @temp
	end
	ALTER procedure [dbo].[get_choice_vocab]
(
	@idQuestion int
)
	As
	begin
	select * from lua_chon_dap_an where cauhoibaithithuid = @idQuestion
	order by newID()
	end