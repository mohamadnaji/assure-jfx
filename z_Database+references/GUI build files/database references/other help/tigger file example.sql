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
