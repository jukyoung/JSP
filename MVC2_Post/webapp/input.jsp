<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<title>Input</title>
<style>
   textarea{ width : 200px; height : 200px;}
</style>
</head>
<body>
<form action="/insert.post" method="post">
  <div>
      <p>ID : </p>
      <input type="text" name="id" class="form-control">
  </div>
  <div>
      <p>POST : </p>
      <textarea name="post" class="form-control"></textarea>
  </div>
  <div>
      <button type="submit" class="btn btn-outline-primary">업로드</button>
  </div>
  </form>
</body>
</html>