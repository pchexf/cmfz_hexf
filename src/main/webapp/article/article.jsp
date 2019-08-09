<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />

<script charset="utf-8" src="${path}/kindeditor/kindeditor-all.js"></script>
<script charset="utf-8" src="${path}/kindeditor/lang/zh-CN.js"></script>
<script>
    KindEditor.create('#editor_id', {
        uploadJson:"${path}/article/upload",
        filePostName:"photo",
        allowFileManager:true,
        fileManagerJson:"${path}/article/queryAllPhoto",
        afterBlur:function (){  //编辑器失去焦点(blur)时执行的回调函数。
            this.sync();  //将编辑器中的内容同步到表单
        }
    });
</script>

<script>
    $(function(){

        /*jqgrid表格*/
        $("#atctable").jqGrid({
            url:"${path}/article/show",
            editurl:"${path}/article/edit",
            datatype : "json",
            autowidth:true,  //自适应父容器
            height:'auto',
            rownumbers:true,
            styleUI:'Bootstrap', //使用BootStrap风格的样式
            rowNum : 2,
            rowList : [ 2,5,10, 20, 30 ],
            pager : '#atcpager',
            viewrecords:true,  //显示总条数
            colNames : [ 'Id', '名字', '图片' ,'内容', '状态', '上传时间','所属上师' ],
            colModel : [
                {name : 'id',width : 55,hidden:true},
                {name : 'title',editable:true,width : 90},
                {name : 'insert_img',editable:true,width : 90},
                {name : 'content',editable:true,width : 100,align : "center"},
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
                {name : 'up_date',width : 80,align : "center"},
                {name : 'guruid',editable:true,width : 80,align : "center"}
            ]
        });

        //增删改查操作
        $("#atctable").jqGrid('navGrid', '#atcpager',
            {edit : false,add : false,add : false,search:false,del : true,edittext:"编辑"}
        );
    });

    /*点击展示详情*/
    $("#btn1").click(function(){
        //选中一行 获取行Id
        var rowId= $("#atctable").jqGrid("getGridParam","selrow");
        //判断是否选中一行
        if(rowId!=null){
            //根据行Id获取行数据
            var row= $("#atctable").jqGrid("getRowData",rowId);

            //展示模态框
            $("#myModal").modal("show");

            //给inout框设置内容
            $("#title").val(row.title);
            $("#guruid").val(row.guruid);
            //给KindEditor框设置内容
            KindEditor.html("#editor_id",row.content);

            //添加按钮
            $("#modalFooter").html("<button type='button' onclick='updateArticle(\""+rowId+"\")' class='btn btn-default'>提交</button><button type='button' class='btn btn-primary' data-dismiss='modal'>关闭</button>");
        }else{
            alert("请选中一行数据");
        }

    });

    /*点击添加文章*/
    $("#btn2").click(function(){

        //给表单置空
        $("#articleFrom")[0].reset();

        //给KindEditor框置空
        KindEditor.html("#editor_id","");

        //展示模态框
        $("#myModal").modal("show");
        //添加按钮
        $("#modalFooter").html("<button type='button' onclick='addArticle()' class='btn btn-default'>提交" +
            "</button><button type='button' class='btn btn-primary' data-dismiss='modal'>关闭</button>");
    });

    /*点击添加按钮操作*/
    function addArticle(){
        //向后台提交
        $.ajax({
            url:"${path}/article/add",
            type:"post",
            dateType:"json",
            data:$("#articleFrom").serialize(),
            success:function(){
                $("#myModal").modal('hide');//隐藏模态框

                $("#atctable").trigger("reloadGrid");//刷新jqGrid
            }
        });
    }

    /*点击删除文章*/
    $("#btn3").click(function(){
        alert("点击删除文章");
    });

    /*修改的提交按钮*/
    function updateArticle(rowId){

        //向后台提交
        $.ajax({
            url:"${path}/article/update?id="+rowId,
            type:"post",
            dateType:"json",
            data:$("#articleFrom").serialize(),
            success:function(){
                $("#myModal").modal('hide');//隐藏模态框
                $("#atctable").trigger("reloadGrid");//刷新jqGrid
            }
        });

    }
    //点击修改
    function change(id,value){

        if(value==1){

            $.ajax({
                url:"${path}/article/update",
                type:"post",
                datType:"JSON",
                data:{"id":id,"status":"2"},
                success:function(data){
                    //页面的刷新
                    $("#atctable").trigger("reloadGrid");
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
                url:"${path}/article/update",
                type:"post",
                datType:"JSON",
                data:{"id":id,"status":"1"},
                success:function(data){
                    //页面的刷新
                    $("#atctable").trigger("reloadGrid");
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
</script>

<%--初始化一个面板--%>
<div class="panel panel-danger">

    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>文章信息</h2>
    </div>

    <ul class="nav nav-tabs">
        <li class="active"><a href="#"><b>文章信息</b></a></li>
    </ul>

    <div class="panel panel-body">
        <button type="button" id="btn1" class="btn btn-info" >查看文章</button>&emsp;
        <button type="button" id="btn2" class="btn btn-success" >添加文章</button>&emsp;
        <button type="button" id="btn3" class="btn btn-warning" >删除文章</button>&emsp;
    </div>

    <%--初始化表单--%>
    <table id="atctable" />

    <%--分页工具栏--%>
    <div id="atcpager" />

</div>
<%--初始化模态框--%>

<div id="myModal" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel">
    <div class="modal-dialog" role="document" style="width: 730px">
        <div class="modal-content">
            <%--模态框标题--%>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="gridSystemModalLabel">Modal title</h4>
            </div>
            <%--模态框内容--%>
            <div class="modal-body">
                <form class="form-horizontal" id="articleFrom">

                    <div class="input-group">
                        <span class="input-group-addon" id="basic-addon1">标题</span>
                        <input id="title" name="title" type="text" class="form-control" aria-describedby="basic-addon1">
                        <span class="input-group-addon" id="basic-addon2">所属上师</span>
                        <input id="guruid" name="guruid" type="text" class="form-control" aria-describedby="basic-addon1">
                        <span class="input-group-addon" id="basic-addon3">封面</span>
                        <input id="upload," name="upload" type="file" class="form-control" aria-describedby="basic-addon1" formmethod="post" enctype="multipart/form-data">
                    </div><br>
                    <div class="input-group">
                        <%--初始化富文本编辑器--%>
                        <textarea id="editor_id" name="content" style="width:700px;height:300px;">

                        </textarea>
                    </div>
                </form>
            </div>
            <%--  模态框按钮  --%>
            <div class="modal-footer" id="modalFooter">
                <%--<button type="button" class="btn btn-default">提交</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>--%>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


