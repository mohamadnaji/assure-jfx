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
	 set @newsalary=(select salary from inserted where @idproc=id) 
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