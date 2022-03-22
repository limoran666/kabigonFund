//执行分页，生成页面效果 任何调用这个函数都会重新加载页面
function generatePage(){
    //获取分页数据
    var pageInfo=getPageInfoRemote();
    //填充表格
    fillTableBody(pageInfo)

}
//访问服务器获取pageInfo数据
function getPageInfoRemote(){

    var ajaxResult=$.ajax(
        {
            "url":"role/get/page/info.json",
            "type":"post",
            "data":{
                "pageNum":window.pageNum,
                "pageSize":window.pageSize,
                "keyword":window.keyword

            },
            "async":false,
            "dataType":"json"

        }
    );
    console.log(ajaxResult);

    //判断当前响应状态码是否为200
    var statusCode=ajaxResult.status;

    //如果当前响应状态码不是200 说明发生错误
    if(statusCode !=200){
        layer.msg("服务器端调用失败,响应状态码为"+statusCode);
        return null;

    }
    //如果响应状态码是200 说明请求处理成功，返回pageInfo
    var resultEntity=ajaxResult.responseJSON;

    var result=resultEntity.result;

    if(result=="FAILED"){
        layer.msg(resultEntity.message);
        return null;
    }
    //确认result为成功后获取pageInfo
    var pageInfo=resultEntity.data;

    //返回pageInfo
    return pageInfo;



}
//填充表格
function fillTableBody(pageInfo){
    // 清除 tbody 中的旧的内容
    $("#rolePageBody").empty();

// 这里清空是为了让没有搜索结果时不显示页码导航条
    $("#Pagination").empty();

    //判断 pageInfo对象是否有效
    if (pageInfo==null||pageInfo==undefined||pageInfo.list==null||pageInfo.list.length==0){
        $("#rolePageBody").append("<tr><td colspan='4'>sorry!data is null!</td></tr>");
        return ;
    }
    //填充tbody
    for (var i=0;i<pageInfo.list.length;i++){

        var role=pageInfo.list[i];
        var roleId=role.id;
        var roleName=role.name;

        var numberTd="<td>"+(i+1)+"</td>";
        var checkboxTd = "<td><input id='"+roleId+"'  class='itemBox' type='checkbox'></td>";
        var roleNameTd = "<td>"+roleName+"</td>";

        var checkBtn = "<button id='"+roleId+"' type='button' class='btn btn-success btn-xs checkBtn'><i class=' glyphicon glyphicon-check'></i></button>"
        var pencilBtn = "<button id='"+roleId+"' type='button' class='btn btn-primary btn-xs pencilBtn'><i class=' glyphicon glyphicon-pencil'></i></button>";
        var removeBtn = "<button id='"+roleId+"' type='button' class='btn btn-danger btn-xs removeBtn' ><i class=' glyphicon glyphicon-remove'></i></button>";

        var buttonTd = "<td>"+checkBtn+" "+pencilBtn+" "+removeBtn+"</td>";
        var tr = "<tr>"+numberTd+checkboxTd+roleNameTd+buttonTd+"</tr>";
        $("#rolePageBody").append(tr);
    }

    generateNavigator(pageInfo);

}
//生成分页页码导航条
function generateNavigator(pageInfo){

    // 获取总记录数
    var totalRecord = pageInfo.total;
  // 声明相关属性
    var properties = { "num_edge_entries": 3, "num_display_entries": 5, "callback": paginationCallBack, "items_per_page": pageInfo.pageSize, "current_page": pageInfo.pageNum - 1, "prev_text": "上一页", "next_text": "下一页"
    }
    // 调用 pagination()函数
    $("#Pagination").pagination(totalRecord, properties);


}
function paginationCallBack(pageIndex,jQuery){
    // 修改 window 对象的 pageNum 属性
    window.pageNum = pageIndex + 1;

    // 调用分页函数
    generatePage();
    // 取消页码超链接的默认行为
    return false;

}


// 6.给页面上的“铅笔”按钮绑定单击响应函数，目的是打开模态框
// 传统的事件绑定方式只能在第一个页面有效，翻页后失效了
// $(".pencilBtn").click(function(){
// alert("aaaa...");
// });
// 使用 jQuery 对象的 on()函数可以解决上面问题
// ①首先找到所有“动态生成”的元素所附着的“静态”元素
// ②on()函数的第一个参数是事件类型
// ③on()函数的第二个参数是找到真正要绑定事件的元素的选择器
// ③on()函数的第三个参数是事件的响应函数
$("#rolePageBody").on("click",".pencilBtn",function(){
// 打开模态框
    $("#editModal").modal("show");
// 获取表格中当前行中的角色名称
    var roleName = $(this).parent().prev().text();
// 获取当前角色的 id
// 依据是：var pencilBtn = "<button id='"+roleId+"' ……这段代码中我们把 roleId 设置到id 属性了
// 为了让执行更新的按钮能够获取到 roleId 的值，把它放在全局变量上
    window.roleId = this.id;
// 使用 roleName 的值设置模态框中的文本框
    $("#editModal [name=roleName]").val(roleName);
});

// 7.给更新模态框中的更新按钮绑定单击响应函数
$("#updateRoleBtn").click(function(){
    // ①从文本框中获取新的角色名称
    var roleName = $("#editModal [name=roleName]").val();
// ②发送 Ajax 请求执行更新
    $.ajax({ "url":"role/update.json", "type":"post", "data":{ "id":window.roleId, "name":roleName
        },"dataType":"json", "success":function(response){
            var result = response.result;
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
    });
// ③关闭模态框
    $("#editModal").modal("hide");
});

function showConfirmModal(roleArray){

    $("#confirmModal").modal("show");
    $("#roleNameSpan").empty();

    window.roleIdArray=[];

    //遍历roleArray数组
    for (var i=0;i<roleArray.length;i++){
        var role=roleArray[i];
        var roleName=role.roleName;
        $("#roleNameSpan").append(roleName+"<br/>");

        var roleId=role.roleId;
        //调用数组对象的push方法 存入新的元素
        window.roleIdArray.push(roleId);
    }



}

// 声明专门的函数用来在分配 Auth 的模态框中显示 Auth 的树形结构数据
function fillAuthTree() {
// 1.发送 Ajax 请求查询 Auth 数据
    var ajaxReturn = $.ajax({ "url":"assgin/get/all/auth.json", "type":"post", "dataType":"json", "async":false
    });
    if(ajaxReturn.status != 200) {
        layer.msg(" 请 求 处 理 出 错 ！ 响 应 状 态 码 是 ： "+ajaxReturn.status+" 说 明 是 ："+ajaxReturn.statusText);
        return ;
    }
// 2.从响应结果中获取 Auth 的 JSON 数据
// 从服务器端查询到的 list 不需要组装成树形结构，这里我们交给 zTree 去组装
    var authList = ajaxReturn.responseJSON.data;
// 3.准备对 zTree 进行设置的 JSON 对象
    var setting = { "data": { "simpleData": {
// 开启简单 JSON 功能
                "enable": true, // 使用 categoryId 属性关联父节点，不用默认的 pId 了
                "pIdKey": "categoryId"
            },"key": {
// 使用 title 属性显示节点名称，不用默认的 name 作为属性名了
                "name": "title"
            }
        },"check": { "enable": true
        }
    };
// 4.生成树形结构
// <ul id="authTreeDemo" class="ztree"></ul>
    $.fn.zTree.init($("#authTreeDemo"), setting, authList);
// 获取 zTreeObj 对象
    var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
// 调用 zTreeObj 对象的方法，把节点展开
    zTreeObj.expandAll(true);
// 5.查询已分配的 Auth 的 id 组成的数组
    ajaxReturn =$.ajax({
        "url":"assign/get/assigned/auth/id/by/role/id.json",
        "type":"post",
        "data":{
            "roleId":window.roleId
        },
        "dataType":"json","async":false
    });
    if(ajaxReturn.status != 200) {
        layer.msg(" 请 求 处 理 出 错 ！ 响 应 状 态 码 是 ： "+ajaxReturn.status+" 说 明 是 ："+ajaxReturn.statusText);
        return ;
    }
// 从响应结果中获取 authIdArray
    var authIdArray = ajaxReturn.responseJSON.data;
// 6.根据 authIdArray 把树形结构中对应的节点勾选上
// ①遍历 authIdArray
    for(var i = 0; i < authIdArray.length; i++) {
        var authId = authIdArray[i];
// ②根据 id 查询树形结构中对应的节点
        var treeNode = zTreeObj.getNodeByParam("id", authId);
// ③将 treeNode 设置为被勾选
// checked 设置为 true 表示节点勾选
        var checked = true;
// checkTypeFlag 设置为 false，表示不“联动”，不联动是为了避免把不该勾选的勾选上
        var checkTypeFlag = false;
// 执行
        zTreeObj.checkNode(treeNode, checked, checkTypeFlag);
    }
}
