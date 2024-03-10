<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<jsp:include page="/header.jsp"/>
<jsp:include page="/menu.jsp"/>

<!--Page Heading-->
<div class="row">
    <div class="col-12">
        <div class="page-title-box">
            <h4 class="page-title float-left">Disciplinas</h4>

            <ol class="breadcrumb float-right">
                <li class="breadcrumb-item">
                    <a href="${pageContext.request.contextPath}/">Home</a>
                </li>
                <li class="breadcrumb-item">
                    <a href="#">Cadastros</a>
                </li>
                <li class="breadcrumb-item">
                    <a href="${pageContext.request.contextPath}/DisciplinaListar">Disciplina</a></li>
                <li class="breadcrumb-item active">Disciplinas</li>
            </ol>

            <div class="clearfix"></div>
        </div>
    </div>
</div>

<!-- DataTables Example -->
<div class="card shadow col-md-12">
    <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary">Disciplinas</h6>
    </div>

    <div class="card-body">
     
                <div class="form-row">
                    <input type="hidden" class="form-control" name="iddisciplina" id="iddisciplina" required value="${disciplina.idDisciplina}" style="text-transform: uppercase"/>

                    <div class="form-group col-md-8">
                         <label for="nomedisciplina" class="col-form-label">Nome disciplina</label>
                        <input type="text" class="form-control descricao" id="nomedisciplina" name="nomedisciplina" required value="${disciplina.nomeDisciplina}" style="text-transform: uppercase">
                    </div>
                </div>
                <div class="form-row form-group">
                    <div class="form-group col-md-8">
                            <div class="col-form-label">Semestre</div>
                            <select name="idsemestre" id="idsemestre" class="form-control">
                                <option value="">Selecione</option>
                                <c:forEach var="semestre" items="${semestres}">
                                    <option value="${semestre.idSemestre}"
                                            ${disciplina.semestre.idSemestre == semestre.idSemestre ? "selected" : ""}>
                                            ${semestre.numSemestre}
                                    </option>
                                </c:forEach>
                            </select>
                    </div>
                </div>
               <tr><td>
                        <input type="hidden" name="situacao" id="situacao" value="${disciplina.situacao}" readonly="readonly"/>
                </td></tr>
               <div class="form-group">
                    <button  class="btn btn-success" type="submit"  id="submit" onclick="validarCampos()">Cadastrar</button>
                    <button type="reset" id="limpar"  class="btn btn-danger">Limpar</button>
                    <button class="btn btn-warning">
                       <a href="${pageContext.request.contextPath}/DisciplinaListar" class="btn-warning">Voltar</a>
                    </button>
               </div>
        
                 
    </div>
</div>
    <script>
   $(document).ready(function () {
	console.log("entrei na ready do documento");
     });
    function validarCampos() {
        console.log("entrei na validação de campos");
        if (document.getElementById("nomedisciplina").value == '') {
                        Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: 'Verifique a nome da disciplina!',
                                showConfirmButton: false,
                                timer: 1000
                        });
                        $("#nomedisciplina").focus();
        } else if (document.getElementById("idsemestre").value == '') {
                        Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: 'Verifique o tipo de disciplina!',
                                showConfirmButton: false,
                                timer: 1000
                        });
                        $("#idsemestre").focus();
        } 
        else {
                gravarDados();
        }
    }
       
        function gravarDados() {
        console.log("Gravando dados...");
                $.ajax({
                        type: 'post',
                        url: 'DisciplinaCadastrar',
                        data: {
                                iddisciplina: $('#iddisciplina').val(),
                                nomedisciplina: $('#nomedisciplina').val(),
                                situacao: $('#situacao').val(),
                                idsemestre: $('#idsemestre').val(),
                        },
                        success:
                                function(data) {
                                        console.log("resposta servlet->");
                                        console.log(data);
                                        if (data == 1) {
                                                Swal.fire({
                                                        position: 'center',
                                                        icon: 'success',
                                                        title: 'Sucesso',
                                                        text: 'Disciplina gravada com sucesso!',
                                                        showConfirmButton: false,
                                                        timer: 1000
                                                });
                                        } else {
                                                Swal.fire({
                                                        position: 'center',
                                                        icon: 'error',
                                                        title: 'Erro',
                                                        text: 'Não foi possível gravar a disciplina!',
                                                        showConfirmButton: false,
                                                        timer: 1000
                                                });
                                        }
                                        setTimeout(() => {window.location.href = "${pageContext.request.contextPath}/DisciplinaListar"}, 2000);
                                },
                        error:
                                function (data) {
                                        setTimeout(() => {window.location.href = "${pageContext.request.contextPath}/DisciplinaListar"}, 2000);
                                }
        });
    }
</script>

<%@include file="/footer.jsp" %>