<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@tag pageEncoding="UTF-8" %>
<%@attribute name="title" %>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>${title}</title>
</head>
<t:head/>
<body>
<header class="row-fluid">
    <div class="" style="background-color:#2A5ABE!important;background-image: none!important; ">
        <div class="container">
            <a class="" style="color: #ffffff;font-size: 40px;">项目工具集</a>
        </div>
    </div>
</header>
<section class="row-fluid">
    <div class="page-continer container">
        <jsp:doBody/>
    </div>
</section>

</body>
<t:foot/>
