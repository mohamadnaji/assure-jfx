create trigger triggername4
on emp
instead of insert
as 
	begin
	declare @sal float
	set @sal =(select salary from inserted)
	if(@sal>1000)
		 begin
			print 'congrat you get+1000 on salary just for fun'
			set @sal=@sal+1000
		end
	insert emp select name,lastname,@sal,capital from inserted 
	end
go