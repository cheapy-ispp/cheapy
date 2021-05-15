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
<link type="text/css" rel="stylesheet" href="https://unpkg.com/bootstrap-vue@latest/dist/bootstrap-vue.css" />

<style>
.fa {
  padding: 20px;
  font-size: 30px;
  text-align: center;
  text-decoration: none;
  margin: 5px 2px;
  border-radius: 50%;
}

.column-card {
  float: left;
  width: 33%;
  margin-top: 6px;
  padding: 20px;
  text-align: center;
}

.row-card:after {
  content: "";
  display: table;
  clear: both;
}

.column-info {
  float: left;
  width: 25%;
  margin-top: 6px;
  padding: 20px;
  text-align: center;
}

.row-info {
  padding: 20px;
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
	width: 30%;
}
</style>

<cheapy:layout pageName="contactInfo">

    <h2 class="titulo" style="font-family: 'Lobster'; font-size:200%; padding:10px; margin-bottom:20px;">Información de contacto</h2>
    <h3 style="font-family: 'Lobster'; text-align:center; font-size:150%;  color: #325a80; padding:10px; margin-bottom:20px;">
    ¿Tienes alguna duda? Puedes hacérnosla llegar a través de los siguientes medios:</h3>
    <section>
     <div class="containerr" style="text-align: center;">
     <p style="font-family: 'Lobster'; text-align:center; font-size:150%;  color: #325a80; padding:10px; margin-bottom:20px;">
    	Cuentas oficiales de Cheapy</p>
     	<div class="card">
	    	<img src="/resources/images/Logo Cheapy.png" class="card-img-top" alt="Cheapy" style="width: 350px; height: 350px;">
		    <div class="card-body">
		    <h5 class="card-title">Eat fast, eat Cheapy</h5>
		    </div>
	    </div>
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
	</section>
	
    <section>
        <div class="containerr" style="text-align: center;">
        <p style="font-family: 'Lobster'; text-align:center; font-size:150%;  color: #325a80; padding:10px; margin-bottom:20px;">
    		Equipo detrás de Cheapy</p>
            <div class="row-card">
	    		<div class="column-card">
	                <div class="card">
	                    <img src="/resources/images/pablo.jpeg" class="card-img-top" alt="Pablo" style="width: 350px; height: 350px;">
	                    <div class="card-body">
	                    <h5 class="card-title">Pablo</h5>
	                    <h6 class="card-subtitle mb-2 text-muted">Franco Sánchez</h6>
	                    <p class="card-text">Computer Engineering student in Software Engineering</p>
	                    <a href="https://github.com/pabfrasan" target="_blank" class="btn btn-outline-dark">
	                        <i class="fa fa-github-alt"></i> GitHub Profile</a>
	                	</div>
	                </div>
	            </div>
	            <div class="column-card">
	                <div class="card">
	                    <img src="/resources/images/abraham.jpeg" class="card-img-top"  alt="Abraham" style="width: 350px; height: 350px;">
	                    <div class="card-body">
	                    <h5 class="card-title">Abraham</h5>
	                    <h6 class="card-subtitle mb-2 text-muted">García Villalobos</h6>
	                    <p class="card-text">Computer Engineering student in Software Engineering</p>
	                    <a href="https://github.com/AbrahamSFC" target="_blank" class="btn btn-outline-dark">
	                        <i class="fa fa-github-alt"></i> GitHub Profile</a>
	                	</div>
	                </div>
	            </div>
	            <div class="column-card">
	                <div class="card">
	                    <img src="/resources/images/martin.jpeg" class="card-img-top" alt="Martin" style="width: 350px; height: 350px;">
	                    <div class="card-body">
	                    <h5 class="card-title">Martín Arturo</h5>
	                    <h6 class="card-subtitle mb-2 text-muted">Guerrero Romero</h6>
	                    <p class="card-text">Computer Engineering student in Software Engineering</p>
	                    <a href="https://github.com/Martinagr32" target="_blank" class="btn btn-outline-dark">
	                        <i class="fa fa-github-alt"></i> GitHub Profile</a>
	                	</div>
	                </div>
				</div>
				<div class="column-card">
            
	                <div class="columna col card">
	                    <img src="/resources/images/gabo.jpeg" class="card-img-top" alt="Gabriel" style="width: 350px; height: 350px;">
	                    <div class="card-body">
	                    <h5 class="card-title">Gabriel</h5>
	                    <h6 class="card-subtitle mb-2 text-muted">Gutiérrez Prieto</h6>
	                    <p class="card-text">Computer Engineering student in Software Engineering</p>
	                    <a href="https://github.com/gabgutpri" target="_blank" class="btn btn-outline-dark">
	                        <i class="fa fa-github-alt"></i> GitHub Profile</a>
	                	</div>
	                </div> 
	            </div>
	            <div class="column-card">
	                <div class="columna col card">
	                    <img src="/resources/images/thibout.jpeg" class="card-img-top" alt="Thibaut" style="width: 350px; height: 350px;">
	                    <div class="card-body">
	                    <h5 class="card-title">Thibaut</h5>
	                    <h6 class="card-subtitle mb-2 text-muted">Lopez</h6>
	                    <p class="card-text">Computer Engineering student in Software Engineering</p>
	                    <a href="https://github.com/Thiloparn" target="_blank" class="btn btn-outline-dark">
	                        <i class="fa fa-github-alt"></i> GitHub Profile</a>
	                	</div>
	                </div>
	            </div>
	            <div class="column-card">
	                <div class="columna col card">
	                    <img src="/resources/images/soto.jpeg" class="card-img-top" alt="David" style="width: 350px; height: 350px;">
	                    <div class="card-body">
	                    <h5 class="card-title">David</h5>
	                    <h6 class="card-subtitle mb-2 text-muted">Soto Ponce</h6>
	                    <p class="card-text">Computer Engineering student in Software Engineering</p>
	                    <a href="https://github.com/davsotpon" target="_blank" class="btn btn-outline-dark">
	                        <i class="fa fa-github-alt"></i> GitHub Profile</a>
	                	</div>
	                </div>
				</div>
				<div class="column-card">
            
	                <div class="columna col card">
	                    <img src="/resources/images/javi.jpeg" class="card-img-top" alt="Javier" style="width: 350px; height: 350px;">
	                    <div class="card-body">
	                    <h5 class="card-title">Javier</h5>
	                    <h6 class="card-subtitle mb-2 text-muted">Granja Naranjo</h6>
	                    <p class="card-text">Computer Engineering student in Software Engineering</p>
	                    <a href="https://github.com/javgranar" target="_blank" class="btn btn-outline-dark">
	                        <i class="fa fa-github-alt"></i> GitHub Profile</a>
	                	</div>
	                </div>
	            </div>
	            <div class="column-card">
	                <div class="columna col card">
	                    <img src="/resources/images/flor.jpeg" class="card-img-top" alt="Florentina" style="width: 350px; height: 350px;">
	                    <div class="card-body">
	                    <h5 class="card-title">Florentina</h5>
	                    <h6 class="card-subtitle mb-2 text-muted">Correa López</h6>
	                    <p class="card-text">Computer Engineering student in Software Engineering</p>
	                    <a href="https://github.com/flocorlop" target="_blank" class="btn btn-outline-dark">
	                        <i class="fa fa-github-alt"></i> GitHub Profile</a>
	                	</div>
	                </div>
	            </div>
	            <div class="column-card">
	                <div class="columna col card">
	                    <img src="/resources/images/antonio.jpeg" class="card-img-top" alt="Antonio" style="width: 350px; height: 350px;">
	                    <div class="card-body">
	                    <h5 class="card-title">Antonio</h5>
	                    <h6 class="card-subtitle mb-2 text-muted">Vidal Pérez</h6>
	                    <p class="card-text">Computer Engineering student in Software Engineering</p>
	                    <a href="https://github.com/davsotpon" target="_blank" class="btn btn-outline-dark">
	                        <i class="fa fa-github-alt"></i> GitHub Profile</a>
	                	</div>
	                </div>
				</div>
				<div class="column-card">
			
	                <div class="columna col card">
	                    <img src="/resources/images/angel.jpeg" class="card-img-top" alt="Angel" style="width: 350px; height: 350px;">
	                    <div class="card-body">
	                    <h5 class="card-title">Angel</h5>
	                    <h6 class="card-subtitle mb-2 text-muted">Caballero Domínguez</h6>
	                    <p class="card-text">Computer Engineering student in Software Engineering</p>
	                    <a href="https://github.com/angcabdom" target="_blank" class="btn btn-outline-dark">
	                        <i class="fa fa-github-alt"></i> GitHub Profile</a>
	                	</div>
	                </div>
	            </div>
	            <div class="column-card">
	                <div class="columna col card">
	                    <img src="/resources/images/abel.jpeg" class="card-img-top" alt="Abel" style="width: 350px; height: 350px;">
	                    <div class="card-body">
	                    <h5 class="card-title">Abel</h5>
	                    <h6 class="card-subtitle mb-2 text-muted">Morante Caraballo</h6>
	                    <p class="card-text">Computer Engineering student in Software Engineering</p>
	                    <a href="https://github.com/abemorcardc" target="_blank" class="btn btn-outline-dark">
	                        <i class="fa fa-github-alt"></i> GitHub Profile</a>
	                	</div>
	                </div>
	            </div>
            </div>
        </div>
        <button id='volver' type="button" onclick="history.back()" name="volver atrás" value="volver atrás" style="font-family: 'Lobster';">Volver</button>
    </section>
    
    


  	

</cheapy:layout>
