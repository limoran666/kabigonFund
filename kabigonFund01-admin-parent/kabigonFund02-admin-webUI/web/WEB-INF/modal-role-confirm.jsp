<%--
  Created by IntelliJ IDEA.
  User: limoran
  Date: 2022/3/14
  Time: 23:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="confirmModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">尚筹网系统弹窗</h4>
            </div>
            <div class="modal-body">
                <h4>请确认是否要删除下列角色</h4>
                <span id="roleNameSpan"></span>
            </div>
            <div class="modal-footer">
                <button id="removeRoleBtn" type="button" class="btn btn-primary"> 确认删除
                </button>
            </div>
        </div>
    </div>
</div>