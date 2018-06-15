<%--
  Created by IntelliJ IDEA.
  User: ZHANGYANG
  Date: 2016/10/19
  Time: 20:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<html>
<t:page title="代码生成">

    <div class="row-fluid">
        <div class="col-lg-12 line-down">
            <h2>java代码生成工具</h2>
        </div>
    </div>

    <form id="form">
        <div class="row-fluid ">
            <div class="col-lg-6">
                <div class="form-horizontal">
                    <div class="form-group">
                        <label class="col-lg-3 control-label">项目路径:</label>
                        <div class="col-lg-9"><input name="path" placeholder="绝对路径，以/结束" class="form-control"
                                                     value=""></div>
                    </div>
                </div>
            </div>


            <div class="col-lg-6">
                <div class="form-horizontal">
                    <div class="form-group">
                        <label class="col-lg-3 control-label">表名:</label>
                        <div class="col-lg-9">
                            <select id="tableName" name="tableName" class="form-control">

                            </select>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row-fluid">
            <div class="col-lg-12">
            <div class="col-lg-6">
                <div class="form-horizontal">
                    <div class="form-group">
                        <label class="col-lg-3 control-label">子包名:</label>
                        <div class="col-lg-9"><input name="thPack" placeholder="例如：user" class="form-control" value="">
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-6"></div>
        </div>
            </div>

        <div class="row-fluid">
            <div class="col-lg-12">
            <div class="col-lg-7">
                <div class="form-horizontal">
                    <div class="form-group">
                        <label class="col-lg-3 control-label">model存放项目:</label>
                        <div class="col-lg-9">
                            <select id="model" name="model" class="form-control">
                                <option value=''>不需要</option>
                                <option value='cbg.common'>cbg.common</option>
                                <option value='cbg-app'>cbg-app</option>
                                <option value='cbg-chembeango'>cbg-chembeango</option>
                                <option value='cbg-chembeango-common'>cbg-chembeango-common</option>
                                <option value='cbg-news'>cbg-news</option>
                                <option value='cbg-news-common'>cbg-news-common</option>
                                <option value='cbg-onlinestore'>cbg-onlinestore</option>
                                <option value='cbg-onlinestore-common'>cbg-onlinestore-common</option>
                                <option value='cbg-enonlinestore'>cbg-enonlinestore</option>
                                <option value='cbg-enonlinestore-common'>cbg-enonlinestore-common</option>
                                <option value='cbg-multimedia'>cbg-multimedia</option>
                                <option value='cbg-multimedia-common'>cbg-multimedia-common</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-5"></div>
        </div>
        </div>
        <div class="row-fluid">
            <div class="col-lg-12">
            <div class="col-lg-7">
                <div class="form-horizontal">
                    <div class="form-group">
                        <label class="col-lg-3 control-label">dao存放项目:</label>
                        <div class="col-lg-9">
                            <select id="dao" name="dao" class="form-control">
                                <option value=''>不需要</option>
                                <option value='cbg.common'>cbg.common</option>
                                <option value='cbg-app'>cbg-app</option>
                                <option value='cbg-chembeango'>cbg-chembeango</option>
                                <option value='cbg-chembeango-common'>cbg-chembeango-common</option>
                                <option value='cbg-news'>cbg-news</option>
                                <option value='cbg-news-common'>cbg-news-common</option>
                                <option value='cbg-onlinestore'>cbg-onlinestore</option>
                                <option value='cbg-onlinestore-common'>cbg-onlinestore-common</option>
                                <option value='cbg-enonlinestore'>cbg-enonlinestore</option>
                                <option value='cbg-enonlinestore-common'>cbg-enonlinestore-common</option>
                                <option value='cbg-multimedia'>cbg-multimedia</option>
                                <option value='cbg-multimedia-common'>cbg-multimedia-common</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-5"></div>
            </div>
        </div>
        <div class="row-fluid">
            <div class="col-lg-12">
            <div class="col-lg-7">
                <div class="form-horizontal">
                    <div class="form-group">
                        <label class="col-lg-3 control-label">dao-xml存放项目:</label>
                        <div class="col-lg-9">
                            <select id="daoxml" name="daoxml" class="form-control">
                                <option value=''>不需要</option>
                                <option value='cbg.common'>cbg.common</option>
                                <option value='cbg-app'>cbg-app</option>
                                <option value='cbg-chembeango'>cbg-chembeango</option>
                                <option value='cbg-chembeango-common'>cbg-chembeango-common</option>
                                <option value='cbg-news'>cbg-news</option>
                                <option value='cbg-news-common'>cbg-news-common</option>
                                <option value='cbg-onlinestore'>cbg-onlinestore</option>
                                <option value='cbg-onlinestore-common'>cbg-onlinestore-common</option>
                                <option value='cbg-enonlinestore'>cbg-enonlinestore</option>
                                <option value='cbg-enonlinestore-common'>cbg-enonlinestore-common</option>
                                <option value='cbg-multimedia'>cbg-multimedia</option>
                                <option value='cbg-multimedia-common'>cbg-multimedia-common</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-5"></div>
        </div>
        </div>
        <div class="row-fluid">
            <div class="col-lg-12">
            <div class="col-lg-7">
                <div class="form-horizontal">
                    <div class="form-group">
                        <label class="col-lg-3 control-label">service存放项目:</label>
                        <div class="col-lg-9">
                            <select id="service" name="service" class="form-control">
                                <option value=''>不需要</option>
                                <option value='cbg.common'>cbg.common</option>
                                <option value='cbg-app'>cbg-app</option>
                                <option value='cbg-chembeango'>cbg-chembeango</option>
                                <option value='cbg-chembeango-common'>cbg-chembeango-common</option>
                                <option value='cbg-news'>cbg-news</option>
                                <option value='cbg-news-common'>cbg-news-common</option>
                                <option value='cbg-onlinestore'>cbg-onlinestore</option>
                                <option value='cbg-onlinestore-common'>cbg-onlinestore-common</option>
                                <option value='cbg-enonlinestore'>cbg-enonlinestore</option>
                                <option value='cbg-enonlinestore-common'>cbg-enonlinestore-common</option>
                                <option value='cbg-multimedia'>cbg-multimedia</option>
                                <option value='cbg-multimedia-common'>cbg-multimedia-common</option>
                            </select>
                        </div>
                    </div>
                </div>

            </div>
            <div class="col-lg-5"></div>
        </div>
        </div>
    </form>
    <div class="row-fluid">
        <div class="col-lg-3 col-lg-offset-9 ">
            <div class="form-horizontal">
                <div class="form-group text-center">
                    <button class="btn btn-success">开始</button>
                </div>
            </div>
        </div>
    </div>
</t:page>
<script>
    $(function () {
        $.post(ctx + "/code/getTableNames", {}, function (data) {
            if (data.items == null || data.items.length == 0)
                return;
            var html = "";
            $.each(data.items, function (i, d) {
                html += "<option value='" + d + "'>" + d + "</option>";
            });
            $("#tableName").html(html);
        }, "json");

        $(".btn-success").click(function () {

            $.post(ctx + "/code/createClass", $("#form").serialize(), function (data) {
                if (data == 1)
                    alert("创建成功");
                else
                    alert("创建失败")
            });
        });
    })


</script>
</html>
