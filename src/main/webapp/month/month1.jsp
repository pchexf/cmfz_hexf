<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

    <%--javascript 方式获取SDK--%>
    <script src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>
    <!-- 引入 ECharts 文件 -->
    <script src="${path}/js/echarts.js"></script>
    <script type="text/javascript">


            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            var goEasy = new GoEasy({
                appkey: "BC-2a34cc77845e4d1c97858dca6dbea0df"
            });
            goEasy.subscribe({
                channel: "myChannel", //管道标识
                onMessage: function (message) {

                    //将json字符串转为json对象
                    var data= JSON.parse(message.content);
                    console.log(data);

                    // 指定图表的配置项和数据
                    var option = {
                        title: {
                            text: '用户注册量展示', //标题
                            link:"${path}/main/main.jsp",
                            target:"self",
                            subtext:"用户信息"
                        },
                        tooltip: {},  //鼠标的提示
                        legend: {
                            // show:false,  是否展示 选项
                            data:['男',"女"]  //选项
                        },
                        xAxis: {
                            data: data.month  //横坐标
                        },
                        yAxis: {},  //纵坐标   自适应
                        series: [{
                            name: '男',
                            type: 'bar',
                            data: data.boys
                        },{
                            name: '女',
                            type: 'bar',
                            data: data.girls
                        }]
                    };

                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);

                }


        });
    </script>
    <script type="text/javascript">


            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            $.post("${path}/user/selectMonth",function(data){

                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: '用户注册量展示', //标题
                        link:"${path}/main/main.jsp",
                        target:"self",
                        subtext:"用户信息"
                    },
                    tooltip: {},  //鼠标的提示
                    legend: {
                        // show:false,  是否展示 选项
                        data:['男','女']  //选项
                    },
                    xAxis: {
                        data: data.month  //横坐标
                    },
                    yAxis: {},  //纵坐标   自适应
                    series: [{
                        name: '男',
                        type: 'bar',
                        data: data.boys
                    },{
                        name: '女',
                        type: 'bar',
                        data: data.girls
                    }]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);

            },"json");


    </script>


<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 600px;height:400px;"></div>

