<%--
  Created by IntelliJ IDEA.
  User: limoran
  Date: 2022/3/14
  Time: 21:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="zh-CN">

<%@include file="include-head.jsp"%>
<body>
<link rel="stylesheet" href="css/pagination.css">
<link rel="stylesheet" href="ztree/zTreeStyle.css"/>
<script type="text/javascript" src="js/my-role.js"></script>
<script type="text/javascript" src="js/jquery/jquery.pagination.js" ></script>
<script type="text/javascript" src="js/layer/layer.js" ></script>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript">
    $(function (){
        window.pageNum=1;
        window.pageSize=5;
        window.keyword="";

        generatePage();

        //给查询按钮绑定单击响应函数
        $("#searchBtn").click(function (){
            //获取关键字数据给全局变量
            window.keyword=$("#keywordInput").val();
            //调用分页函数刷新
            generatePage();
 });
        //点击新增按钮打开模态框
        $("#showAddModalBtn").click(function (){
           $("#addModal").modal("show");
        });

        $("#saveRoleBtn").click(function (){
            //用户在文本框输入的名称
            //④获取用户在文本框中输入的角色名称
            //#addModal表示找到整个模态框
            //空格表示在后代元素中继续查找
            //[name=roleName]表示匹配name属性等于roleName的元素

            var roleName = $.trim($("#addModal [name=roleName]").val());
            //发送Ajax请求
            $.ajax({
                "url":"role/save.json",
                "type":"post",
                "data":{
                    "name":roleName

                },
                "dataType":"json",
                "success":function (response){
                    var result=response.result;
                    if (result=="SUCCESS"){
                        layer.msg("操作成功");
                        //重新加载分页
                        window.pageNum=9999999;
                        generatePage();
                    }
                    if (result=="FAILED"){
                        layer.msg("操作失败");
                    }

                },
                "error":function (response){
                    layer.msg(response.status+"")
                }


            });
            //关闭模态框
            $("#addModal").modal("hide");

            //清理模态框
            $("#addModal [name=roleName]").val("");



        });
        /*使用jQuery对象的on()函数可以解决上面问题
④首先找到所有“动态生成”的元素所附着的“静态”元素
②on（）函数的第一个参数是事件类型
③on（）函数的第二个参数是找到真正要绑定事件的元素的选择器
③on（）函数的第三个参数是事件的响应函数
*/
        $("#rolePageBody").on("click",".pencilBtn",function(){
            //打开模态框
            $("#editModal").modal("show");
            //获取表格中当前行中的角色名称
            var roleName = $(this).parent().prev().text();
            window.roleId = this.id;
            //使用roleName的值设置模态框中的文本框
            $("#editModal [name=roleName]").val(roleName);
        });

        //给更新模态框中的更新按钮绑定单击响应函数
        $("#updateRoleBtn").click(function (){

            var roleName=$("#editModal [name=roleName]").val();
            //发送Ajax请求
            $.ajax({
                "url":"role/update.json",
                "data":{
                    "id":window.roleId,
                    "name":roleName
                },
                //服务器返回的请求按json处理
                "dataType":"json",
                "success":function (response){
                    var result=response.result;
                    if (result=="SUCCESS"){
                        layer.msg("操作成功!");
                        generatePage();
                    }
                    if (result=="FAILED"){
                        layer.msg("操作失败！"+response.message);
                    }

                },
                "error":function (response){
                    layer.msg(response.status+"");
                }

            });
            $("#editModal").modal("hide");
        });
        $("#removeRoleBtn").click(function (){
            var requestBody=JSON.stringify(window.roleIdArray);
         $.ajax(
             {
                 "url":"role/remove/by/role/id/array.json",
                 "type":"post",
                 "data":requestBody,
                 "contentType":"application/json;charset=UTF-8",
                 "dataType":"json",
                 "success":function(response){
                     var result=response.result;
                     if(result == "SUCCESS") {
                         layer.msg("操作成功！");
                    // 重新加载分页数据
                         generatePage();
                     }
                     if(result == "FAILED") {
                         layer.msg("操作失败！"+response.message);
                     }
                 },"error":function(response){
                    layer.msg(response.status+" "+response.statusText);
                }

             }


         );
         $("#confirmModal").modal("hide");
     });
        //给单条删除按钮绑定响应函数
        $("#rolePageBody").on("click",".removeBtn",function (){
            var roleName=$(this).parent().prev().text();

            //创建一个role的对象
            var roleIdArray=[{
                roleId:this.id,
                roleName:roleName
            }];
            showConfirmModal(roleIdArray);

        });

        $("#summaryBox").click(function (){
            var currentStatus=this.checked;
            $(".itemBox").prop("checked",currentStatus);
        });
        //11.全选、全不选的反向操作
        $("#rolePageBody").on("click",".itemBox",function(){
        //获取当前已经选中的。itemBox的数量
            var checkedBoxCount = $(".itemBox:checked").length;
        //获取全部.itemBox的数量
            var totalBoxCount = $(".itemBox").length;
        //使用二者的比较结果设置总的checkbox
            $("#summaryBox").prop("checked",checkedBoxCount == totalBoxCount);
        });
        //给批量删除的按钮绑定单击响应函数
        $("#batchRemoveBtn").click(function (){
            //创建数组对象
           var roleArray=[];
            $(".itemBox:checked").each(
                function (){
                    var  roleId=this.id;
                   var roleName=$(this).parent().next().text();
                   roleArray.push({
                       "roleId":roleId,
                       "roleName":roleName
                   });

                }
            );

            //检查roleArray长度是否为0
            if (roleArray.length==0){
                layer.msg("请至少选择一个执行删除");
                return ;
            }

            showConfirmModal(roleArray);
        });

        // 13.给分配权限按钮绑定单击响应函数
        $("#rolePageBody").on("click",".checkBtn",function(){
            // 打开模态框
            $("#assignModal").modal("show");
            // 在模态框中装载树 Auth 的形结构数据
           fillAuthTree();
        });

        // 14.给分配权限模态框中的“分配”按钮绑定单击响应函数
        $("#assignBtn").click(function(){
// ①收集树形结构的各个节点中被勾选的节点
// [1]声明一个专门的数组存放 id
            var authIdArray = [];
// [2]获取 zTreeObj 对象
            var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
// [3]获取全部被勾选的节点
            var checkedNodes = zTreeObj.getCheckedNodes();
// [4]遍历 checkedNodes
            for(var i = 0; i < checkedNodes.length; i++) {
                var checkedNode = checkedNodes[i];
                var authId = checkedNode.id;
                authIdArray.push(authId);
            }
// ②发送请求执行分配
            var requestBody = { "authIdArray":authIdArray, // 为了服务器端 handler 方法能够统一使用 List<Integer>方式接收数据，roleId 也存入数组
                "roleId":[window.roleId]
            };
            requestBody = JSON.stringify(requestBody);
            $.ajax({ "url":"assign/do/role/assign/auth.json", "type":"post", "data":requestBody, "contentType":"application/json;charset=UTF-8", "dataType":"json", "success":function(response){
                    var result = response.result;
                    if(result == "SUCCESS") {
                        layer.msg("操作成功！");
                    }
                    if(result == "FAILED") {
                        layer.msg("操作失败！"+response.message);
                    }
                },"error":function(response) {
                    layer.msg(response.status+" "+response.statusText);
                }
            });
            $("#assignModal").modal("hide");

        });


        });

</script>
<%@ include file="include-nav.jsp"%>
<div class="container-fluid">
    <div class="row">
        <%@include file="include-sidebar.jsp"%>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keywordInput" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button id="batchRemoveBtn" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button id="showAddModalBtn" type="button" class="btn btn-primary" style="float:right;" ><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody">

                            </tbody>
                            <tfoot>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"></div>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/modal-role-add.jsp"%>
<%@include file="/WEB-INF/modal-role-edit.jsp"%>
<%@include file="/WEB-INF/modal-role-confirm.jsp"%>
<%@include file="/WEB-INF/modal-role-assign-auth.jsp"%>
</body>
</html>
