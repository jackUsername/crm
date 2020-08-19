<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath=request.getScheme()+"://"+request.getServerName()+
":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
	<base href="<%=basePath%>"/>
<meta charset="UTF-8">
	<%--"${pageContext.request.contextPath}/bootstrap_3.3.0/css/bootstrap.min.css"
	${pageContext.request.contextPath}/--%>
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

	<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
	<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

<script type="text/javascript">

	$(function(){
		//给模态窗口绑定事件
		$("#addBtn").click(function () {
            $(".time").datetimepicker({
                minView: "month",
                language:  'zh-CN',
                format: 'yyyy-mm-dd',
                autoclose: true,
                todayBtn: true,
                pickerPosition: "bottom-left"
            });

            $("#createActivityModal").modal("show");
			$.ajax({
				url:"setting/getUser.do",
				type:"get",
				dataType:"json",
				success:function (data) {

				    var html="<option></option>";
                    $.each(data,function (i,n) {
                        //alert(n.name+n.id);
                        html+="<option value='"+n.id+"'>"+n.name+"</option>";
                    })
                    $("#create-Owner").html(html);


					//将当前登录的用户，设置为下拉框默认的选项，取到当前用户的id
					//el表达式在js中使用必须使用""
					var id="${user.id}";
					$("#create-Owner").val(id);

                    $("#createActivityModal").modal("show");
            }
				
			})

        })

		$("#saveBtn").click(function () {
       $.ajax({
                url:"workbench/add.do",
                data:{
                    "owner":$.trim($("#create-Owner").val()),
                    "name":$.trim($("#create-name").val()),
                    "startDate":$.trim($("#create-startDate").val()),
                    "endDate":$.trim($("#create-endDate").val()),
                    "cost":$.trim($("#create-cost").val()),
                    "description":$.trim($("#create-description").val())

                },
                type:"get",
                dataType:"json",
                success:function (data) {
                   // alert(data)
              if(data.success){

                  //pageList(1,2);
                  pageList(1,$("#activityPage").bs_pagination('getOption','rowsPerPage'));
    //清空添加操作模态窗口中的数据
    $("#activtyModelFrom")[0].reset();

    //添加成功后
	//刷新市场活动信息列表（局部刷新）
	$("#createActivityModal").modal("hide");
}else {
    alert("添加失败")
      }
                }
            })

        })
		//页面加载完毕之后触发一个方法
		//默认加载完毕默认加载两条数据
		pageList(1,2);

		//为查询按钮绑定事件，触发pageList方法
		$("#searchBtn").click(function () {

		    $("#hidden-name").val($.trim($("#search-name").val()));
            $("#hidden-owner").val($.trim($("#search-owner").val()));
            $("#hidden-startDate").val($.trim($("#search-startDate").val()));
            $("#hidden-endDate").val($.trim($("#search-endDate").val()));

			pageList(1,2);
        })
		//为全选的复选框绑定事件，触发全选操作
		$("#qx").click(function () {
			$("input[name=xz]").prop("checked",this.checked);
        })
		//$("input[name=xz]").click(function () {
    $("#activityBoby").on("click",$("input[name=xz]"),function(){
		$("#qx").prop("checked",$("input[name=xz]").length==$("input[name=xz]:checked").length)

	})


		//为删除按钮绑定事件
		$("#deleteBtn").click(function () {
			//找到复选框中所有选择的jquery对象
			var $xz=$("input[name=xz]:checked");
			if($xz.length==0){
			    alert("请选择需要删除的记录");
			}else{
			    if(confirm("确定删除所选中的记录吗？")){
                    //拼接参数
                    var param="";
                    //将$xz中的每一个dom对象遍历出来，取其value值，就相当于取得了需要删除的记录的id
                    for(var i=0;i<$xz.length;i++){
                        param+="id="+$($xz[i]).val();
                        //如不是最后一个元素，需要在后面追加一个$符
                        if(i<$xz.length-1){
                            param+="&";
                        }
                    }
                    //alert(param);
                    $.ajax({
                        url:"workbench/activity/delete.do",
                        data:param,

                        type:"get",
                        dataType:"json",
                        success:function (data) {
                            if(data.success){
                                //删除成功后
                                //pageList(1,2);
                                pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

                            }else {
                                alert("删除市场活动失败");
                            }
                        }
                    })
				}

			}

        })

//为修改按钮帮顶事件，为了打开修改按钮的模态窗口

$("#editBtn").click(function () {
	var $xz=$("input[name=xz]:checked");

	if($xz.length==0){
	    alert("请选择要修改的记录");
	}else  if($xz.length>1){
	    alert("只能选择一条记录进行修改");
	}else {
	    var id=$xz.val();
        //$("#editActivityModal").modal("show");
        $.ajax({
            url:"workbench/activity/getUserListAndActivity.do",
            data:{
               "id":id
            },
            type:"get",
            dataType:"json",
            success:function (data) {
               //alert("111111111111111111111111");

                //处理所有者的下拉框
                var html1 = "<option></option>";
                //alert("111111111111111")
                $.each(data.dataList,function (i,n) {
                    //alert(n.name+n.id);
                   //
					var dataList=data.dataList;
                   // alert(n.name+n.id);
                    html1 += "<option value='"+n.id+"'>"+n.name+"</option>";
                    //alert("执行到这里");
                })
                $("#edit-owner").html(html1);
                //alert("执行成功");
                //alert("执行到这里"+data.activity.id);
                //处理单条
                $("#edit-id").val(data.activity.id);
                $("#edit-name").val(data.activity.name);
                $("#edit-owner").val(data.activity.owner);
                $("#edit-startDate").val(data.activity.startDate);
                $("#edit-endDate").val(data.activity.endDate);
                $("#edit-cost").val(data.activity.cost);
                $("#edit-description").val(data.activity.description);
               //所有值都添加完毕之后，打开模态窗口
				$("#editActivityModal").modal("show");

            }
        })
	        }
       })

		//为更新按钮绑定事件，执行市场活动的修改操作
		$("#updateBtn").click(function () {


            $.ajax({
                url:"workbench/update.do",
                data:{
                    "id":$.trim($("#edit-id").val()),
                    "owner":$.trim($("#edit-owner").val()),
                    "name":$.trim($("#edit-name").val()),
                    "startDate":$.trim($("#edit-startDate").val()),
                    "endDate":$.trim($("#edit-endDate").val()),
                    "cost":$.trim($("#edit-cost").val()),
                    "description":$.trim($("#edit-description").val())

                },
                type:"get",
                dataType:"json",
                success:function (data) {
                    // alert(data)
                    if(data.success){


                       //pageList(1,2);
                        pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
                            ,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

                        //添加成功后
                        //刷新市场活动信息列表（局部刷新）
                        $("#editActivityModal").modal("hide");
                    }else {
                        alert("修改失败")
                    }
                }
            })

        })
	});
	/*
	对于所有的关系数据库，做前端分页相关操作的基础组件
	就是pageNO和pageSize
	PageNo:页码
	pageSize:每页展现的记录数
	 */
	function pageList(pageNo,pageSize) {
	    //将全选的复选框干掉
	    $("#qx").prop("checked",false);

	    //查询时，将影藏域中保存的信息取出来，重新赋予到搜索中
        $("#search-name").val($.trim($("#hidden-name").val()));
        $("#search-owner").val($.trim($("#hidden-owner").val()));
        $("#search-startDate").val($.trim($("#hidden-startDate").val()));
        $("#search-endDate").val($.trim($("#hidden-endDate").val()));

        $.ajax({
            url:"workbench/pageList.do",
            data:{
                     "pageNo":pageNo,
				      "pageSize":pageSize,
				     "name" :$.trim($("#search-name").val()),
				     "owner":$.trim($("#search-Owner").val()),
				     "startDate":$.trim($("#search-startDate").val()),
				     "endDate":$.trim($("#search-endDate").val()),
            },
            type:"get",
            dataType:"json",
            success:function (data) {
                //[{市场活动1},{2},{3}]
				var html="";
				$.each(data.dataList,function (i,n) {
				   // alert(i);
					//每一个n就是每一个市场活动对象
                html+='<tr class="active">';
                html+='<td><input type="checkbox" name="xz" value="'+n.id+'"/></td>';
                html+='<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/detail.do?id='+n.id+'\';">'+n.name+'</a></td>';
                html+='<td>'+n.owner+'</td>';
                html+='<td>'+n.startDate+'</td>';
                html+='<td>'+n.endDate+'</td>;'
                html+='</tr>';
            })
        $("#activityBoby").html(html);
				//计算总页数
				var totalPages=data.total%pageSize==0?data.total/pageSize:parseInt(data.total/pageSize)+1;
				//数据处理完毕之后
                $("#activityPage").bs_pagination({
                    currentPage: pageNo, // 页码
                    rowsPerPage: pageSize, // 每页显示的记录条数
                    maxRowsPerPage: 20, // 每页最多显示的记录条数
                    totalPages: totalPages, // 总页数
                    totalRows: data.total, // 总记录条数

                    visiblePageLinks: 3, // 显示几个卡片

                    showGoToPage: true,
                    showRowsPerPage: true,
                    showRowsInfo: true,
                    showRowsDefaultInfo: true,

					//该回调函数时在，点击分页组件的时候触发的
                    onChangePage : function(event, data){
                        pageList(data.currentPage , data.rowsPerPage);
                    }
                });

            }
        })
    }
	
</script>
</head>
<body>
<input type="hidden" id="hidden-name"/>
<input type="hidden" id="hidden-owner"/>
<input type="hidden" id="hidden-startDate"/>
<input type="hidden" id="hidden-endDate"/>



	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form id="activtyModelFrom" class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-Owner">

									<%--<option>张三</option>
									<option>李四</option>
									<option>王五</option>--%>
								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-name">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startDate">
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control  time" id="create-endDate">
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<%--data-dismiss="modal"
					表示关闭模态窗口--%>

					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
					<input type="hidden" id="edit-id"/>
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-owner">

								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-name" value="发传单">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control  time" id="edit-startDate" >
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-endDate" >
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" >
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-description">123</textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="updateBtn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="search-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="search-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control" type="text" id="search-startDate" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control" type="text" id="search-endDate">
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" id="searchBtn">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary"id="addBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="qx" /></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="activityBoby">
						<%--<tr class="active">
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
                            <td>2020-10-10</td>
                            <td>2020-10-20</td>
                        </tr>--%>
					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 30px;">

				<div id="activityPage">

				</div>
			</div>
			
		</div>
		
	</div>
</body>
</html>