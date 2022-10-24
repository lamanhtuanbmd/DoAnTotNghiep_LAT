# DoAnTotNghiep_LAT

HƯỚNG DẪN CÀI ĐẶT
Cần cài đặt: 
1. Xamp
2. MySQL
3. Android studio

Link tải file php: https://drive.google.com/drive/folders/1AvxA6ekQx8eJxNpO3yAnW4JgijGhwHCa?usp=sharing
I.	Cài đặt server
1.	Copy thư mục AppFood trong file nén vừa tải về
2.	Dán thư mục AppFood vừa copy vào BÊN TRONG thư mục "htdocs" được chứa trong đường dẫn máy nơi lưu trữ phần mềm XAMPP sau khi cài đặt.

II.	Tạo database
1.	Dùng XAMPP khởi động Apache và MySQL 
2.	Ấn nút “Admin” của MySQL để mở PHPMyAdmin trên trình duyệt.
3.	Giao diện PHPMyAdmin hiện lên, chọn New để tạo mới một database
4.	Đặt tên db là “AppFood” và chọn chế độ utf8_unicode_ci -> Create
5.	Sau khi tạo xong, chọn Import để import file appfood.sql trong file nén vừa tải về -> Go

III.	 Chỉnh sửa đường dẫn Ipv4 và Chạy Code
1.	Mở dự án android bằng Android studio
2.	Bên cột danh sách các file bên trái : 
Chọn Lib -> java -> com.example.lib -> common -> Url
3.	Sửa Ipv4Address thành địa chỉ Ipv4 của máy cá nhân
4.	Xong
*Lưu ý: Luôn giữ cho web phpMyAdmin bật
 
