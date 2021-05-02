<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<style>
.fa {
  padding: 20px;
  font-size: 30px;
  text-align: center;
  text-decoration: none;
  margin: 5px 2px;
  border-radius: 50%;
}

.column-info {
  float: left;
  width: 25%;
  margin-top: 6px;
  padding: 20px;
  text-align: center;
}

.row-info:after {
  content: "";
  display: table;
  clear: both;
}

.landingPage {
	height: 80px;
}

.barImage {
	width: 60%;
}
</style>

<cheapy:layout pageName="contactInfo">

    <h2 class="titulo" style="font-family: 'Lobster'; font-size:200%; padding:10px; margin-bottom:20px;">Información de contacto</h2>
    <p style="font-family: 'Lobster'; text-align:center; font-size:150%;  color: #325a80; padding:10px; margin-bottom:20px;">
    ¿Tienes alguna duda? Puedes hacérnosla llegar a través de los siguientes medios:</p>
    
    <div style="text-align: center;">
    	<img src="/resources/images/bar2-CI.png" class = "barImage" style="border-radius: 8px;">
    </div>
    

	<div class="row-info">
	    <div class="column-info">
	    	<a href="https://twitter.com/cheapyispp" target="_blank" class="fa fa-twitter"></a>
	    	<p><b>@cheapyispp</b></p>
	    	​
	    </div>
	    <div class="column-info">
	    	<a href="https://www.instagram.com/cheapyispp/" target="_blank" class="fa fa-instagram"></a>
	    	<p><b>@cheapyispp</b></p>​
	    </div>
	    <div class="column-info">
	    	<a href="mailto:cheapy.ispp@gmail.com" target="_blank" class="fa fa-envelope"></a>
	    	<p><b>cheapy.ispp@gmail.com</b></p>
	    	​
	    </div>
	    <div class="column-info">
	    	<a href="https://github.com/cheapy-ispp" target="_blank" class="fa fa-github"></a>
	    	<p><b>cheapy-ispp</b></p>
	    	​
	    </div>
	    <!--
	    <div class="column">
	    	<a href="https://cheapy-ispp.github.io/landing-page/" class=""><img src="/resources/images/Logo Cheapy LP.png" class = "landingPage"></a>
	    	<p><b>Landing Page</b></p>
	    </div>
	    -->
	</div>
	


  	

</cheapy:layout>
