<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>

<cheapy:layout pageName="clients">
	<script>
		function pagNum(pagina) {
			paginaAct = pagina + 1;
			document.write("Página " + paginaAct + " <br />");
		}
	</script>
    <h2 class="titulo" style="font-family: 'Lobster'; text-align:center; font-size:200%; padding:10px"><fmt:message key="clients"/></h2>
    
	<c:if test="${empty clientLs }">
		<p id="vacio" >No hay ningun Cliente.</p>
	</c:if>
	<c:if test="${not empty clientLs }">
	<div class="table-responsive">
	    <table id="clientTable" class="table table-striped">
	        <thead>
	        <tr>
	
	        	<th><fmt:message key="name"/></th>
	        	<th><fmt:message key="user"/></th>
	        	<th><fmt:message key="enabled"/></th>   
	            <th> </th>
	        </tr>
	        </thead>
	        <tbody>
	        <c:forEach items="${clientLs}" var="client">
	            <tr>
	
	                <td>
	                    <c:out value="${client.name}"/>
	                </td>
	                <td>
	                    <c:out value="${client.usuar.username}"/>
	                </td>
	                <td>
	                	<c:if test="${client.usuar.enabled == 'true'}">
			            	<c:out value="Si"/>
			            </c:if>	
			            <c:if test="${client.usuar.enabled == 'false'}">
			                 <c:out value="No"/>
			             </c:if>
	                </td>
	                <td>
		                <spring:url value="/administrators/clients/{username}" var="clientUrl">
		                        <spring:param name="username" value="${client.usuar.username}"/>
		                </spring:url>
		                <div class="btn-detalles">
	                		<button type="button" role="link" onclick="window.location='${fn:escapeXml(clientUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
	                		<span class="glyphicon glyphicon-info-sign" aria-hidden="true" style="padding: 5px"> </span>
		                	<fmt:message key="details"/></button>
	            		</div>
	                </td>     
	            </tr>
	        </c:forEach>
	        </tbody>
	    </table>
    </div>
    <div class="text-center">
    	<script type="text/javascript">
			          
    		pagNum(${page});
								
		</script>
    </div>
    <div class="row-pag-btn">
	    <div class="column-pag-btn" style="text-align: left;">
	    	<c:if test='${page!=0}'>
		    	<spring:url value="/administrators/clients/page/{page}" var="clientListUrl">
		    		<spring:param name="page" value="${page-1}"/>
		    	</spring:url>
		    	<button type="button" class="btn-pag" role="link" onclick="window.location='${fn:escapeXml(clientListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
				<span class="glyphicon 	glyphicon glyphicon-arrow-left" aria-hidden="true" style="padding: 5px"> </span>
				Pág. anterior</button>
		    </c:if>
	    	​
	    </div>
	    <div class="column-pag-btn" style="text-align: right;">
	    	<c:if test="${nextPage > 0}">
    	
		    	<spring:url value="/administrators/clients/page/{page}" var="clientListUrl">
		    		<spring:param name="page" value="${page+1}"/>
		    	</spring:url>
		    	<button type="button" class="btn-pag"  role="link" onclick="window.location='${fn:escapeXml(clientListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
				<span class="glyphicon 	glyphicon glyphicon-arrow-right" aria-hidden="true" style="padding: 5px"> </span>
				Pág. siguiente</button>
			</c:if>​
		</div>
	</div>
    </c:if>
</cheapy:layout>
