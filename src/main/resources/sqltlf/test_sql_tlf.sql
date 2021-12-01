#namespace("jfinal")
    #sql("test_sql_tlf")
        select b.*,u.user_name from blog b join user u on b.user_id = u.user_id

        #for(x : data)
            #(for.first ? "where": "and") #(x.key) = '#(x.value)'
        #end

        #if(username != null)
            -- where u.user_name = '#(username)'
        #end
    #end
#end