CREATE DATABASE web_toeic

CREATE TABLE bai_thi_thu(
  baithithuid int IDENTITY(1,1) PRIMARY KEY NOT NULL ,
  anhbaithithu varchar(255) DEFAULT NULL,
  tenbaithithu varchar(20) DEFAULT NULL,
)
CREATE TABLE nguoi_dung(
  id bigint IDENTITY(1,1) PRIMARY KEY NOT NULL,
  dia_chi varchar(255) DEFAULT NULL,
  email varchar(255)  DEFAULT NULL,
  ho_ten varchar(55)  DEFAULT NULL,
  matkhau varchar(255)  DEFAULT NULL,
  so_dien_thoai varchar(11) DEFAULT NULL,
  vai_tro int DEFAULT NULL,
)

CREATE TABLE ket_qua (
  ketquaid int NOT NULL IDENTITY(1,1) PRIMARY KEY,
  correctlisten int  DEFAULT NULL,
  correctread int DEFAULT NULL,
  thoigian date DEFAULT NULL,
  socaudung int DEFAULT NULL,
  socausai int DEFAULT NULL,
  baithithuid int NOT NULL,
  nguoidungid bigint NOT NULL,
  CONSTRAINT fk_baithithuid_ketqua FOREIGN KEY (baithithuid) REFERENCES bai_thi_thu (baithithuid) ON DELETE CASCADE,
  CONSTRAINT fk_nguoidungid FOREIGN KEY (nguoidungid) REFERENCES nguoi_dung (id) ON DELETE CASCADE
)

CREATE TABLE cau_hoi_bai_thi_thu (
  cauhoibaithithuid int NOT NULL IDENTITY(1,1) PRIMARY KEY,
  audiomp3 varchar(255) DEFAULT NULL,
  correctanswer varchar(255) DEFAULT NULL,
  dap_an_user varchar(255) DEFAULT NULL,
  image_test varchar(255) DEFAULT NULL,
  number int DEFAULT NULL,
  option1 varchar(255) DEFAULT NULL,
  option2 varchar(255) DEFAULT NULL,
  option3 varchar(255) DEFAULT NULL,
  option4 varchar(255) DEFAULT NULL,
  paragraph text,
  question varchar(255) DEFAULT NULL,
  baithithuid int NOT NULL,
  CONSTRAINT fk_baithithuid_cauhoi FOREIGN KEY (baithithuid) REFERENCES bai_thi_thu (baithithuid) ON DELETE CASCADE
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
insert into bai_thi_thu(anhbaithithu,tenbaithithu) values ('','test1')
select * from nguoi_dung 
DECLARE @i int = 41
WHILE @i <= 50 
BEGIN
     insert into cau_hoi_bai_thi_thu (audiomp3,image_test,baithithuid) values ('image_sound\'+CAST(@i as varchar)+'.MP3','image_sound\'+CAST(@i as varchar)+'.png',1)
    SET @i = @i + 1
END