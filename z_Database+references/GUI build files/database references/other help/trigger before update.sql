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