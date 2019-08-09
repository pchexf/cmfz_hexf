<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<script>
    $(function(){
        $("#bntable").jqGrid({
            url : "${path}/user/show",
            //editurl:"${path}/user/edit",
            datatype : "json",
            rowNum : 2,
            autowidth:true,
            height:"auto",
            styleUI:"Bootstrap",
            rowList : [ 2, 4, 6 ],
            pager : '#bnpager',
            viewrecords : true,  //是否展示总条数
            colNames : [ 'Id', '手机', '头像','法名','姓名','性别','所在地','签名','状态','注册时间' ],
            colModel : [
                {name : 'id',width : 55,hidden:true, align : "center"},
                {name : 'phone',editable:true,width : 90, align : "center"},
                {name : 'pic_img',width : 100,editable:true,edittype:"file",align : "center",
                    formatter:function(cellvalue){
                        return "<img src='${path}/upload/photo/"+cellvalue+"' style='width:180px;height:80px' />";
                    },// 返回图片。
                },
                {name : 'ahama',editable:true,width : 80,align : "center"},
                {name : 'name',editable:true,width : 80,align : "center"},
                {name : 'sex',editable:true,width : 80,align : "center"},
                {name : 'city',editable:true,width : 80,align : "center"},
                {name : 'sign',editable:true,width : 80,align : "center"},
                {name : 'status',width : 80,align : "center",
                    formatter:function(cellvalue,option,row){
                        if(cellvalue==1){
                            //展示状态
                            return "<button class='btn btn-success' onclick='change(\""+row.id+"\",\""+cellvalue+"\")'  >不展示</button>";
                        }else{
                            //不展示状态
                            return "<button class='btn btn-danger' onclick='change(\""+row.id+"\",\""+cellvalue+"\")'  >展示</button>";
                        }
                    },
                },
                {name : 'reg_date',width : 80,align : "center"}
            ]

        });

        /*增删改查操作*/
        $("#bntable").jqGrid('navGrid', '#bnpager',
            {edit : false,add : false,del : false}
        );
    });
    //点击修改
    function change(id,value){

        if(value==1){

            $.ajax({
                url:"${path}/user/updateStatus",
                type:"post",
                dataType:"JSON",
                data:{"id":id,"status":"2"},
                success:function(data){
                    //页面的刷新
                    $("#bntable").trigger("reloadGrid");
                    //提示框添加信息
                    $("#showMsg").html(data.message);
                    //展示错误信息
                    $("#show").show();

                    //设置提示框时间
                    setTimeout(function () {
                        //关闭提示框
                        $("#show").hide();
                    }, 3000);
                }
            });
        }else{
            $.ajax({
                url:"${path}/user/updateStatus",
                type:"post",
                dataType:"JSON",
                data:{"id":id,"status":"1"},
                success:function(data){
                    //页面的刷新
                    $("#bntable").trigger("reloadGrid");
                    //提示框添加信息
                    $("#showMsg").html(data.message);
                    //展示错误信息
                    $("#show").show();
                    //设置提示框时间
                    setTimeout(function () {
                        //关闭提示框
                        $("#show").hide();
                    }, 3000);
                }
            });
        }
    }
    function playerChapter() {
        //location.href="${path}/user/esport";

        $.ajax({
            url:"${path}/user/esport/",
            type:"post",
            dataType:"JSON",
            success:function(data){

            }
        });
    }
</script>

<%--初始化面板--%>
<div class="panel panel-info">

    <div class="panel panel-heading">
        <h2>轮播图信息</h2>
    </div>
    <div id="show" class="alert alert-warning alert-dismissible" role="alert"  style="width:200px;display: none">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <strong id="showMsg"></strong>
    </div>
    <div class="panel panel-body">
        <button type="button" id="btn1" class="btn btn-info" onclick="playerChapter()">导出用户信息</button>&emsp;
    </div>

    <%--初始化表单--%>
    <table id="bntable" />

    <%--定义分页工具栏--%>
    <div id="bnpager"></div>

</div>



