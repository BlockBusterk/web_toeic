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
  image_test varchar(255) DEFAULT NULL,
  part int DEFAULT NULL,
  question varchar(255) DEFAULT NULL,
  baithithuid int NOT NULL,
  CONSTRAINT fk_baithithuid_cauhoi FOREIGN KEY (baithithuid) REFERENCES bai_thi_thu (baithithuid) ON DELETE CASCADE
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
	@sl_cauhoi int
)
	As 
	begin
SELECT    TOP (@sl_cauhoi) cauhoibaithithuid, question, part
FROM      cau_hoi_bai_thi_thu
WHERE    (part = @part)
ORDER BY NEWID()
	end

