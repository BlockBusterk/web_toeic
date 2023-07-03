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
insert into nguoi_dung(@email,matkhau) values('h@gmail.com','1')
CREATE TABLE ket_qua (
  cauhoibaithithuid int,
  email varchar(255),
  ngaylambai date,
  dungsai bit,
  part int,
  primary key(cauhoibaithithuid,email,ngaylambai),
 CONSTRAINT fk_cauhoibaithithuid_ketqua FOREIGN KEY (cauhoibaithithuid) REFERENCES cau_hoi_bai_thi_thu (cauhoibaithithuid) ON DELETE CASCADE,
 CONSTRAINT fk_email_nguoidung FOREIGN KEY (@email) REFERENCES nguoi_dung (@email) ON DELETE CASCADE,
)

CREATE TABLE cau_hoi_bai_thi_thu (
  cauhoibaithithuid int NOT NULL IDENTITY(1,1) PRIMARY KEY,
  audiomp3 varchar(255) DEFAULT NULL,
  image_test varchar(255) DEFAULT NULL,
  part int DEFAULT NULL,
  question varchar(255) DEFAULT NULL,
)
create table lua_chon_dap_an(
  cauhoibaithithuid int ,
  choice varchar(1),
  content varchar(255),
  result varchar(255),
  primary key(cauhoibaithithuid,choice),
  CONSTRAINT fk_baithithuid_luachon FOREIGN KEY (cauhoibaithithuid) REFERENCES cau_hoi_bai_thi_thu (cauhoibaithithuid) ON DELETE CASCADE
)

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
	set @num = (select COUNT(*) from ket_qua where email = @email and ngaylambai =CAST( GETDATE() AS Date ))
     SELECT    TOP (1) @idcauhoi= cauhoibaithithuid
    FROM      cau_hoi_bai_thi_thu
    WHERE    (part = @part) and cauhoibaithithuid not in (select cauhoibaithithuid from ket_qua where email = @email and ngaylambai =CAST( GETDATE() AS Date ) )  
    ORDER BY NEWID()
    
    SELECT     cauhoibaithithuid, question, part,@num
        FROM      cau_hoi_bai_thi_thu
    WHERE     cauhoibaithithuid= @idcauhoi
    if @num < 5
    begin
    insert into ket_qua(cauhoibaithithuid,email,ngaylambai,part) values (@idcauhoi,@email,CAST( GETDATE() AS Date),@part)
    end
	end
delete from ket_qua
go
Create procedure get_choice
(
	@idQuestion int
)
	As
	begin
	select * from lua_chon_dap_an where cauhoibaithithuid = @idQuestion
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
DBCC CHECKIDENT ('[cau_hoi_bai_thi_thu]', RESEED, 0);
GO

delete from ket_qua
select * from cau_hoi_bai_thi_thu
select *from ket_qua 
go
Create procedure diem_ngay(
@email varchar (50),
@part tinyint
)
as
begin
  select COUNT(email) from ket_qua where email = @email and dungsai = 1 and ngaylambai = CAST(GetDate() as DATE) and part = @part
end
go
Create procedure diem_thang(
@email varchar (50),
@part tinyint
)
as
begin
  select COUNT(email) from ket_qua where email = @email and dungsai = 1 and Month(ngaylambai) = Month(CAST(GetDate() as DATE)) and YEAR(ngaylambai) = Year(CAST(GetDate() as DATE)) and part = @part
end
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
