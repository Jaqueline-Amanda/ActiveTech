<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>M�dulo Cadastros</h1>
<h2>Menu Principal - Logado: ${sessionScope.nomeusuario} - ${sessionScope.tipousuario} -
    <a href="${pageContext.request.contextPath}/UsuarioDeslogar">Sair do Sistema</a></h2>
<c:if test="${sessionScope.tipousuario == 'Administrador'}">
    <jsp:include page="menuAdministrador.jsp"/>
</c:if>
<c:if test="${sessionScope.tipousuario == 'Professor'}">
    <jsp:include page="menuProfessor.jsp"/>
</c:if>
 <c:if test="${sessionScope.tipousuario == 'Aluno'}">
    <jsp:include page="menuAluno.jsp"/>
</c:if>