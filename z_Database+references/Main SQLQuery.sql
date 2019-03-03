use [Project_Prototype]
select 'hi'
ALTER AUTHORIZATION ON DATABASE::[Project_Prototype] TO [Mohamad];
------------- Creating UDT ------------- 
create type udname from varchar(50)
create type udmail from varchar(100)
create type udid from int not null
-- drop type udid
drop type udmail

create rule mail_checker
	as @mail like '[a-zA-Z]%@%.%'
go
--drop rule mail_checker
exec sp_bindrule 'mail_checker','udmail'
go
--exec sp_unbindrule 'udmail','mail_checker';
go

insert into Agent values('fist','lat','comegami.cm')

create type temp as table(
	tid udid primary key identity(1,1),
	clid int,
	agid int,
	cpid int,
	ctid int,
	icid int,
	itid int,
	date date,
	sdate date,
	edate date
)
drop type temp
------------- Creating Table ------------- 
create table Company( --CP
	ID udid primary key identity(1,1),
	Name udname,
	Location varchar(50),
	Fax varchar(50)
)

create table Agent( --AG
	ID udid primary key identity(1,1),
	Name udname,
	Last udname,
	Mail udmail,
)

create table Client( --CL
	ID udid primary key identity(1,1),
	Name udname,
	Last udname,
	Tel varchar(50)
)

create table InsuranceTypes( --IT
	ID udid primary key identity(1,1),
	Code varchar(5),
	Ratio float,
	check ( Ratio >=0 and Ratio <=100)
)
-----misc
 --drop table contrats drop table Agent drop table Client drop table Company drop table InsuranceTypes  
--- ( relation table)
create table CPhaveIT(
	CPID udid ,
	ITID udid ,
	primary key (cpid,itid),
	foreign key (cpid) references company(id),
	foreign key (itid) references Insurancetypes(id)
)

create table Contrats( --CT
	ID udid primary key identity(1,1),
	AGID udid,
	CLID udid,
	CPID udid,
	ITID udid,
	[Start Date] date,
	[End Date] date,
	Price float,
	Description text,
	check(Price >0),
	foreign key (AGID) references agent(id),
	foreign key (CLID) references client(id),
	foreign key (cpid) references company(id),
	foreign key (itid) references Insurancetypes(id)
)

create table PayCollection(
	AGID udid,
	CLID udid,
	CTID udid,
	Date date,
	primary key (CTID,Date),
	foreign key (AGID) references agent(id),
	foreign key (CLID) references client(id),
	foreign key (CTID) references contrats(id)
)
 create table Incidents( --IC
	ID udid primary key identity(1,1),
	AGID udid,
	CLID udid,
	CTID udid,
	Date date,
	CLBILL float,
	Compensation float,
	Rapport text,
	foreign key (AGID) references agent(id),
	foreign key (CLID) references client(id),
	foreign key (CTID) references contrats(id)
)
 --drop table Incidents
-- drop table AGSendCP
create table AGSendCP(
	ID udid primary key identity(1,1),
	AGID udid,
	CPID udid,
	ICID udid,
	Date date,
	foreign key (AGID) references agent(id),
	foreign key (cpid) references company(id),
	foreign key (ICID) references Incidents(id)
)
--drop table agsendcp drop table cphaveit drop table PayCollection drop table incidents
------------- Creating Trigger ------------- 
create trigger CheckValidContrat
on contrats
instead of insert -- data inserted form the user are in the table 'inserted'
as 
	begin
	declare @clid int
	declare @sdate date
	declare @dif int,@cpid int, @agid int
	declare @inscpid int, @insagid int,@valid int
	declare @instype int, @insstartdate date,@insenddate date
	declare @code varchar(5)
	set @valid =0
	-- set @clid =(select id from inserted)
	select @clid=CLID,@inscpid=CPID,@insagid=AGID,@instype=ITID,@insstartdate=[Start Date],@insenddate=[End Date] from inserted
	-- use later cursor to fetch all contrats and if it was with same type check same company otherwise continue
	select @sdate=[End Date],@agid=AGID from Contrats  where CLID=@clid order by [End Date] -- give biggest end date
	
	-- ask dr for if so only check if the new contrats is valid date  --	select @code= code from InsuranceTypes where id=@instype
	--select @cpid=cpid from contrats e, InsuranceTypes t where e.ITID=t.ID and @code= t.Code
	-- select @sdate=[End Date],@agid=AGID,@cpid=cpid  from Contrats e,InsuranceTypes t  where CLID=@clid and e.ITID=t.ID and @code= t.Code order by [End Date] -- give biggest end date
	 -- select @cpid=e.cpid from contrats e, InsuranceTypes t where e.ITID=t.ID and @code= t.Code
	--print @clid print @cpid print @insagid print @sdate print @cpid print @agid
	if(@sdate is not null)
		begin
			set @dif = DATEDIFF(day,getdate(),@sdate) -- return diff days @sdate - getdate() // system database time
			if(@dif>0)
				begin
					if(@agid != @insagid)
						begin print 'error changing ag id is not allowed' end
					else
						if(@cpid is not null and @cpid!=@inscpid)
						begin print 'error chaging company for the same type is not allowed' end
						begin 
						set @valid =1
						end
				end
			else
				begin
					set @valid =1
				end
		end
	else
		begin 
		 set @valid =1
		end
		if(not exists (select ITID from CPhaveIT where CPID=@inscpid and ITID= @instype))
		begin
			print 'Chosen Company does not have specifeid type' 
			set @valid =0
		end
		if(DATEDIFF(day,@insstartdate,@insenddate)<0)
		begin
			print 'Invalid Date input' 
			set @valid =0
		end
		if(@valid =1)
			begin		insert into Contrats SELECT
							[AGID]
						  ,[CLID]
						  ,[CPID]
						  ,[ITID]
						  ,[Start Date]
						  ,[End Date]
						  ,[Price]
						  ,[Description]
					 FROM inserted
		  end
	end
go
drop trigger CheckValidContrat
select ceiling(datediff(MONTH,getdate(),'2020/9/21')/12.0)
select datediff(MONTH,getdate(),'2020/9/21')/12.0
select * from Contrats
select 23%2


-- trigger to valid insert of incident
create trigger Check_Paid_before_incident
on incidents
instead of insert
as	
	begin
	declare @CLID int, @CTID int,@difyear int, @insDate date, @instype int
	declare @EndDate date, @StartDate date, @dif int, @valid int, @paid int
	declare @bill float
	set @valid = 1
	select @CTID = CTID,@insDate=Date,@bill=CLBILL from inserted
	select @EndDate =[End Date], @StartDate = [Start Date], @instype=ITID from Contrats where id=@CTID
	
	--set @dif = DATEDIFF(day,getdate(),@EndDate)
	if(not(datediff(day,@startdate, @insdate)>0 and datediff(day,@insdate,@EndDate)>0)) begin set @valid = 0 print 'Contrat does not include that date' end
	--set @dif = DATEDIFF(MONTH,@StartDate,GETDATE())
	set @dif = DATEDIFF(MONTH,@StartDate,@insDate)
	set @difyear= CEILING(@dif/12.0)
	set @paid = dbo.paid(@CTID)
	if(not(@paid >= @difyear or ( @paid= @difyear -1 and @dif%12=1) ))
		begin
			print 'you have not paid all year contrats broken' -- we can add broken status in contrat
			set @valid =0
		end
	if(@valid=1)
		begin
			declare @code varchar(5),@ratio float,@comp float
			select @code= code,@ratio=ratio from InsuranceTypes where id=@instype
			if(@code='MED')
			begin set @comp = @ratio * @bill /100 end
		 INSERT INTO [dbo].[Incidents]
           select 
			[AGID]
           ,[CLID]
           ,[CTID]
           ,[Date]
           ,[CLBILL]
           ,@comp
		   ,[Rapport]
			from inserted
		 end
	end
go
drop trigger Check_Paid_before_incident

-- inserting some values to the database
insert into Agent(name,last,Mail) values('Ag1','last1','email@ag.com')
insert into InsuranceTypes values('Life',null)

select * from Agent
select * from Client
select * from company
select * from InsuranceTypes
select * from CPhaveIT
select * from Contrats order by [End Date]
select * from Incidents
select * from PayCollection
insert into CPhaveIT values(5,10)
insert into CPhaveIT select top 3 2,id from InsuranceTypes where code='MED'
INSERT INTO [dbo].[Contrats]
           ([AGID]
           ,[CLID]
           ,[CPID]
           ,[ITID]
           ,[Start Date]
           ,[End Date]
           ,[Price]
           ,[Description])
     VALUES
           (23
           ,3
           ,1
           ,6
           ,'2019/12/21'
           ,'2020/12/21'
           ,1000
           ,'what ever you say desc')
GO
INSERT INTO [dbo].[PayCollection]
           ([AGID]
           ,[CLID]
           ,[CTID]
           ,[Date])
     VALUES
           (23
           ,1
           ,1
           ,'2016/12/25')
GO
INSERT INTO [dbo].[Incidents]
           (
           [AGID]
           ,[CLID]
           ,[CTID]
           ,[Date]
           ,[Rapport]
           ,[CLBILL])
     VALUES
           (
           23
           ,1
           ,1
           ,'2017/12/2'
           ,'i broke my hand'
           ,324.134)
GO


begin 
declare @t int
select @t =id from Agent 
print @t
if(@t is not null)
begin print 'i exist ' end
end

--- functions and procedure section

-- procedure to return all information about a client
create procedure ClientReport @clid udid
as 
	declare contrat_cursor cursor for 
			SELECT [ID]
				  ,[AGID]
				  ,[CLID]
				  ,[CPID]
				  ,[ITID]
				  ,[Start Date]
				  ,[End Date]
				  ,[Price]
			  FROM [dbo].[Contrats] where [CLID]=@clid
	
	declare @ctid int
	declare @agid int,@agname udname,@aglast udname
	declare @cpid int,@cpname udname
	declare @itid int, @instype varchar(5), @ratio float
	declare @sdate date, @edate date
	declare @price float
	declare @paid int
	declare @print ClientRP

	open contrat_cursor
	fetch next from contrat_cursor into @ctid,@agid,@clid,@cpid,@itid,@sdate,@edate,@price

	while @@FETCH_STATUS =0
	begin
		select @agname=Name, @aglast=Last from Agent
		select @cpname=Name from Company
		select @instype=Code, @ratio=Ratio from InsuranceTypes
		select @paid= dbo.paid(@ctid);
		insert into @print values(@ctid,@instype,@ratio,@price,@sdate,@edate,@paid,@cpid,@cpname,@agid,@agname,@aglast)
		fetch next from contrat_cursor into @ctid,@agid,@clid,@cpid,@itid,@sdate,@edate,@price
	end
	close contrat_cursor 
	deallocate contrat_cursor 
	select * from Client where id=@clid
	select * from @print
go

exec ClientReport 19;


create type ClientRP as table(
	[Contrat ID] int,
	[Type] varchar(5),
	[Ratio] float,
	[Price] float,
	[Start Date] date,
	[End Date] date,
	[Paid Times] int,
	[Company ID] int,
	[Company Name] udname,
	[Agent ID] udname,
	[Agent Name] udname,
	[Agent Last] udname
)
drop  type clientrp


create function paid (@CTID int)
returns int 
begin
	return (select count(*) from PayCollection where CTID=@ctid)
end

create procedure ClientDebt
--returns table
as
	declare @clid int
	declare @print ClientDept
	declare id_curse cursor for 
		select id from dbo.Client
	open id_curse
	fetch next from id_curse into @clid
	while @@FETCH_STATUS =0
	begin

			declare contrat_cursor cursor for
					select id,agid,[Start Date],[End Date] from Contrats where CLID=@CLID
	
			declare @ctid int,@agid int
			declare @sdate date,@edate date
			declare @paid int

			open  contrat_cursor
			fetch contrat_cursor into @ctid,@agid,@sdate,@edate
			while @@FETCH_STATUS = 0
			begin
				-- later do change end date in contrats to period of year 
				set @paid=dbo.paid(@ctid)
				set @sdate=DATEADD(year,@paid,@sdate)
				declare @i int
				while(DATEDIFF(year,@sdate,getdate())>=0)
				begin
					insert into @print values(@agid,@clid,@ctid,@sdate)
					set @sdate=DATEADD(year,1,@sdate)
				end
				fetch contrat_cursor into @ctid,@agid,@sdate,@edate
			end
			close contrat_cursor 
			deallocate contrat_cursor 
			fetch next from id_curse into @clid
	end
	close id_curse 
	deallocate id_curse 
	select * from  @print
	--Drop Procedure ClientDebt
	go
-----
exec ClientDebt;
insert into PayCollection values(23,3,8,'2010/6/4')

create type ClientDept as table(
	[Agent ID] int,
	[Client ID] int,
	[Contrat ID] int,
	[Date Needed] date
)
drop type clientdept


