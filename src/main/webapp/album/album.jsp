<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />

<script>
    $(function(){
        $("#abtable").jqGrid({
            url : "${path}/album/show",
            editurl: "${path}/album/edit",
            datatype : "json",
            autowidth: true,
            height : "auto",
            styleUI:"Bootstrap",
            rowNum : 3,
            rowList : [3,6,9],
            pager : '#abpager',
            sortname : 'id',
            viewrecords : true,
            sortorder : "desc",
            multiselect : false,
            colNames : [ 'Id', '名称', '作者', '封面','集数', '播音员','简介','评分','发布时间' ],
            colModel : [
                {name : 'id', width : 55,align:'center'},
                {name : 'title', editable: true,width : 90,align:'center'},
                {name : 'author', editable: true,width : 100,align:'center'},
                {name : 'cover_img', editable: true,width : 80,align : "center",edittype:"file",
                    editoptions:{enctype:"multipart/from-data"},
                    formatter: function (cellvalue, options, rowObject) {
                        return "<img src='${path}/upload/photo/" + cellvalue + "' height='50' width='50' />";
                    }
                },
                {name : 'count', editable: true,width : 80,align : "center"},
                {name : 'broadcast', editable: true,width : 80,align : "center"},
                {name:'content', editable: true,align:'center'},
                {name:'score', editable: true,align:'center'},
                {name:'pud_date',align:'center'}
            ],
            subGrid : true, //开启子表格支持
            //subgrid_id  子表格的Id,当开启子表格式,会在主表格中创建一个子表格，subgrid_id就是这个子表格的Id
            subGridRowExpanded : function(subgridId, rowId) {
                addSubGrid(subgridId,rowId);
            },
            /*subGridRowColapsed : function(subgrid_id, row_id) {
                // this function is called before removing the data
                //var subgrid_table_id;
                //subgrid_table_id = subgrid_id+"_t";
                //jQuery("#"+subgrid_table_id).remove();
            }*/
        });
        /*增删改查操作*/
        $("#abtable").jqGrid('navGrid', '#abpager',
            {add : true,edit : true,del : true,addtext:"添加",edittext:"编辑",deltext:"删除"},
            {
                afterSubmit: function (data) {
                    //文件上传
                    $.ajaxFileUpload({
                        url: "${path}/album/upload",
                        type: "post",
                        dataType: "JSON",
                        fileElementId: "cover_img",
                        data: {id: data.responseText},
                        success: function () {
                            //刷新表单
                            $("#bntable").trigger("reloadGrid");
                        }
                    })
                    //一定要有返回值，返回什么都行
                    return "hello";
                },
                closeAfterEdit: true
            },
            {
                afterSubmit: function (data) {
                    //文件上传
                    $.ajaxFileUpload({
                        url: "${path}/album/upload",
                        type: "post",
                        dataType: "JSON",
                        fileElementId: "cover_img",
                        data: {id: data.responseText},
                        success: function () {
                            //刷新表单
                            $("#bntable").trigger("reloadGrid");
                        }
                    })
                    //一定要有返回值，返回什么都行
                    return "hello";
                },
                closeAfterAdd: true
            },
            {}
        );

    });

    //创建子表单
    function addSubGrid(subgridId,rowId) {

        var subgridTableId = subgridId + "table";
        var pagerId = subgridId+"pager";

        //初始化表单,分页工具栏
        $("#" + subgridId).html("<table id='" + subgridTableId+ "'/><div id='"+ pagerId + "'/>");

        //创建表单
        $("#" + subgridTableId).jqGrid({
            url:"${path}/chapter/show?id="+rowId,
            editurl: "${path}/chapter/edit?sid="+rowId,
            datatype : "json",
            pager : "#"+pagerId,
            rowNum : 3,
            rowList : [ 3, 6, 9],
            sortname : 'num',
            sortorder : "asc",
            autowidth:true,
            viewrecords : true,
            height : "auto",
            styleUI:"Bootstrap",
            colNames : [ 'Id', '路径', '音频大小', '音频时长','上传时间' ,'操作'],
            colModel : [
                {name : "id",width : 80,key : true,align : "center"},
                {name : "url", width : 130,editable: true, edittype:"file",align : "center"},
                {name : "size",width : 70,align : "center"},
                {name : "duration",width : 70,align : "center"},
                {name : "up_date",width : 70,align : "center"},
                {name : "url",align : "center",
                    formatter:function(cellVale){
                        return "<a href='#' onclick='playerChapter(\""+cellVale+"\")' ><span class='glyphicon glyphicon-play-circle' /></a>&nbsp;&emsp;&emsp;" +
                            "<a href='#' onclick='downloadChapter(\""+cellVale+"\")' ><span class='glyphicon glyphicon-download' /></a>";
                    }

                }
            ]
        });

        /*子表格增删改查*/
        $("#" + subgridTableId).jqGrid('navGrid',"#" + pagerId,
            {edit : false,add : true,del : true},
            {},
            {
                afterSubmit: function (data) {
                    //文件上传
                    $.ajaxFileUpload({
                        url: "${path}/chapter/upload",
                        type: "post",
                        dataType: "JSON",
                        fileElementId: "url",
                        data: {id: data.responseText},
                        success: function () {
                            //刷新表单
                            $("#abtable").trigger("reloadGrid");
                        }
                    })
                    //一定要有返回值，返回什么都行
                    return "hello";
                },
                closeAfterAdd: true
            },
            {}
        );
    }

    //下载
    function downloadChapter(fileName) {
        location.href="${path}/chapter/download?fileName="+fileName;
    }
    /*
    * 播放的方式有两种
    *
    * 1.如下
    * 2.将下载代码中的 attachment 改为 inline 在线播放
    *
    * */
    function playerChapter(fileName) {
        //展示模态框
        $("#audioModal").modal("show");
        //播放
        $("#playAudio").attr("src","${path}/upload/music/"+fileName);
    }
</script>

<%--初始化面板--%>
<div class="panel panel-info">

    <div class="panel panel-heading">
        <h2>专辑信息</h2>
    </div>

    <ul class="nav nav-tabs">
        <li class="active"><a href="#">专辑信息</a></li>
    </ul>

    <div class="panel panel-body">
        <button type="button" class="btn btn-success" >添加专辑</button>
        <button type="button" class="btn btn-info" >修改专辑</button>
    </div>

    <div id="show" class="alert alert-warning alert-dismissible" role="alert"  style="width:200px;display: none">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <strong id="showMsg"></strong>
    </div>

    <%--初始化表单--%>
    <table id="abtable"/>

    <%--定义分页工具栏--%>
    <div id="abpager"></div>
    <%--播放模态框--%>
    <div id="audioModal" class="modal fade" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <audio id="playAudio" src="" controls/>
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

</div>

