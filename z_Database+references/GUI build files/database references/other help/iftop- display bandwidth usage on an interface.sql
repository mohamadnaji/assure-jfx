use [testy]
select * from Emp
select * from App
select * from [app-emp]

begin 
	declare @name udname
	declare @last udname
	select @name = name , @last = lastname from emp where id=8 order by salary
	select @name, @last
end

delete from emp where id =5
insert into emp values('user1','last1',324)

alter table emp 
	add foreign key (appid) references app(id);

ALTER AUTHORIZATION ON DATABASE::testy TO [sa];
CREATE LOGIN amir   
    WITH PASSWORD = '123';  
GO  
ALTER AUTHORIZATION ON DATABASE::testy TO amir;

select distinct salary
from emp e1
where 3 = (select count(distinct salary)
from emp e2
where e1.salary <= e2.salary)

select distinct salary,(select count(distinct salary)
from emp e2
where e1.salary <= e2.salary)
from emp e1

select  emp.name ,emp.salary from emp order by salary offset 2 ROWs
select  emp.name ,emp.salary,3 as 'come' from emp order by salary desc
select  emp.name ,emp.salary from emp  where id =6 order by salary ROWS fetch next 1 rows only 

-------------------------------jointure example---------------------------------------------------
select em.name ,em.lastname , ap.location from emp as em ,app as ap , [app-emp] as apem where em.id=apem.empid and ap.id=apem.appid

-------------------------------while example ---------------------
begin 
declare @a int;
set @a=1;
while(@a<11)
begin 
print @a
set @a=@a+1 
 end
end

-------------------------------------------cursor example---------------------------
begin 
declare @id int
declare @name varchar(50)
declare cursortest cursor for
	 select id from emp 

open cursortest 

fetch next from cursortest into @id

while @@FETCH_STATUS=0
begin
set @name= (select name from emp where id=@id)
print @name
fetch next from cursortest into @id
end
close cursortest 
deallocate cursortest 
end


------------------------------------

select sum(salary) as totalsalary from emp 
select sum(capital) as totalcapital from emp
select * from Report 
insert into emp values ('user43','last423',2000,3000)
------------------------ trigger example ----------------
 ---moved alone to trigger file
create trigger triggername
on Emp
after insert 
as 
	begin 
		declare @newsal float
		declare @newcap float
		set @newsal = (select top 1 salary from emp order by id desc)
		set @newcap = (select top 1 capital from emp order by id desc)
		update report set [total capital]=@newcap + [total capital]
		update report set [total salary]=@newsal+ [total salary]
		end
go
disable trigger triggername on emp;
enable trigger triggername on emp;
----------before add  and using tables inserted and deleted table that are automaticly created by sqlserver 
------------ those 2 table are virtuel and used inside trigger to make condition ..... 
------------- deleted table contain old data and inserted contain new data we can join them by id and insert value into history table with date
---------- see https://dba.stackexchange.com/questions/170645/trigger-to-insert-data-row-into-new-table-when-old-table-is-updated
create trigger triggername2
on emp
after update
as 
	begin
	 declare @oldsalary float
	 declare @newsalary float
	 declare @idproc int
	 set @idproc = (select id from deleted)
	 set @oldsalary= (select salary from deleted)
	 set @newsalary=(select salary from emp where @idproc=id) 
	 print 'new salary is '
	 print @newsalary
	 print 'old salary'
	 print @oldsalary
	 print ' on id'
	 print @idproc
	end
go
--test
drop trigger triggername2
select * from emp
update emp set salary =1000 where id=6
---------------------trigger that update inserted value use mode instead of :  or here i will use update
-- see https://stackoverflow.com/questions/8987097/trigger-that-updates-just-the-inserted-row
-- can we update inserted table and insert all new values using inseration from inserted as in url?? see down
create trigger triggername3
on emp
instead of update--,insert
as 
	begin
	 declare @oldsalary float
	 declare @newsalary float
	 declare @idproc int
	 set @idproc = (select id from deleted)
	 set @oldsalary= (select salary from deleted)
	 set @newsalary=(select salary from inserted where @idproc=id)  -- emp is considered same as before 
	 if(@oldsalary > 1000)
		 begin
		 print 'you get a bonus of 10% tooo because you have 1000+ salary already!!'
		 set @newsalary=@newsalary*1.1
		 end
	--else
		--begin
		--update emp 
		--end

	update emp set salary = @newsalary where id=@idproc
	end
go
drop trigger triggername3
select * from emp
update emp set salary =1000 where id=6

--------insert case 
create trigger triggername4
on emp
instead of insert
as 
	begin
	declare @sal float
	set @sal =(select salary from inserted)
	if(@sal>1000)
		 begin
			set @sal=@sal+1000
		end
	print 'hello'
	insert emp select name,lastname,@sal,capital from inserted 
	alter table inserted drop column id
	--insert emp select * from inserted 
	end
go
select * from emp
drop trigger triggername4
insert into emp values('user32','last32',2000,3241)

------------------------------------------ stored procedure example---------------
-- see https://www.w3schools.com/sql/sql_stored_procedures.asp
create procedure showemp
as 
select * from emp 
go

exec showemp;
-------------------------------- procedure with one parameter 
--- exmple print the id of n'd best salary 
-- we can select  emp.name ,emp.salary from emp order by salary offset @order-1 ROWS fetch next 1 rows only
-- do later get order of all table 
create procedure showreport @order int, @tata varchar(50)
as 
	select distinct salary ,@tata as hello
from emp e1
where @order = (select count(distinct salary)
from emp e2
where e1.salary <= e2.salary)
go
select  emp.name ,emp.salary from emp order by salary desc offset 2-1 ROWS fetch next 1 rows only
exec showreport 2,'hi'; -- 1 will be the @order
drop procedure showreport
--------------------------------function --------------------------------
--Function must return a value but in Stored Procedure it is optional. Even a procedure can
-- return zero or n values. Functions can have only input parameters for it whereas Procedures 
--can have input or output parameters . Functions can be called from Procedure whereas Procedures 
--cannot be called from a Function.
create function func1 (@par1 int)
returns int
begin 
return @par1
end

-- test this func1:
select dbo.func1(2)
----more complex: 
-- see https://www.codeproject.com/Articles/1201868/%2FArticles%2F1201868%2FTable-Valued-Functions-in-SQL-Server
create function getname(@id int)
returns table
as 
	return select e.name,e.lastname,e.salary from emp as e where @id=e.id

select * from  dbo.getname(7)
select * from emp

----------------user defined data type
---------- benefit say that we need to use name varchar(40) later we need to set it to 50 we have to search for
---------- it everywhere but in case of udt just change it's definition and it will change everywhere else
---- to delet a type all defined on it must be dropped or changed see video 'sql server 2008 - user defined type'
--- https://weblogs.asp.net/alex_papadimoulis/426930


create type dbo.udname from varchar(50)
go
create type dbo.udstatus from varchar(20)
go
create type dbo.udid from int not null 
go
drop type name
drop type status 
drop type id

begin 
declare @a dbo.name
set @a='hello to you'
print @a
end

create table student(
	id udid primary key not null  identity(1,1),
	name udname,
	lastname udname,
	stat dbo.udstatus
)
drop table student
sp_help student

insert into student values('user1','last1','iew')
select *  from student
alter table student alter column name varchar(50)
alter table student alter column lastname varchar(50)

create type infost as table(
	name udname, lastname udname , stat udstatus)

begin
declare @test infost
insert @test (name,lastname,stat) values ('he','ji','jiwe')
insert student select * from @test
end

select * from student

---------------regular expresion example with user defined data type
create table testrg(
 email varchar(50)
 check ( email like '[a-zA-Z]%@%.%')
)	
drop table testrg
select * from testrg
insert into testrg values ('kjwe@gmail.jwoe')

create type mail from varchar(50)
go
create rule mail_checker
	as @mail like '[a-zA-Z]%@%.%'
go
exec sp_bindrule 'mail_checker','mail'
go
create table testrg2(
 email mail
)	
drop table testrg2
select * from testrg2
insert into testrg2 values ('kjwe@gmailjwoe')

use testy
create table ratio(
	ratio float,
)
insert into ratio values(23.23)

insert into ratio values(101)
select * from ratio

