<html>
<head>
    <meta charset="utf-8">
    <title>Seller Error Message</title>
    <link href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="alert alert-dismissable alert-danger">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4>
                    ERROR!
                </h4> <strong>${msg}</strong> <a href="${url}" class="alert-link">Automatically redirect in 3s</a>
            </div>
        </div>
    </div>
</div>


<script>
    setTimeout('location.href="${url}',3000);
</script>

</body>
</html>