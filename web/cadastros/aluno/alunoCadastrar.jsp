<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<jsp:include page="/header.jsp"/>
<jsp:include page="/cadastros/menuLogado.jsp"/>

<!--Page Heading-->
<div class="row">
    <div class="col-12">
        <div class="page-title-box">
            <h4 class="page-title float-left">Alunos</h4>

            <ol class="breadcrumb float-right">
                <li class="breadcrumb-item">
                    <a href="${pageContext.request.contextPath}/">Home</a>
                </li>
                <li class="breadcrumb-item">
                    <a href="#">Cadastros</a>
                </li>
                <li class="breadcrumb-item">
                    <a href="${pageContext.request.contextPath}/AlunoListar">Aluno</a></li>
                <li class="breadcrumb-item active">Alunos</li>
            </ol>

            <div class="clearfix"></div>
        </div>
    </div>
</div>

                <!-- DataTables Example -->
<div class="card shadow col-md-12">
    <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary">Alunos</h6>
    </div>

    <div class="card-body">
        
                <div class="form-row form-group">
                    <input type="hidden" class="form-control" name="idaluno" id="idaluno" required value="${aluno.idAluno}" style="text-transform: uppercase"/>
                    <input type="hidden" class="form-control" name="idpessoa" id="idpessoa" required value="${aluno.idPessoa}" style="text-transform: uppercase"/>

                    <div class="form-group col-md-8">
                         <label for="nome" class="col-form-label">Nome</label>
                        <input type="text" class="form-control descricao" id="nome" name="nome" required value="${aluno.nome}" style="text-transform: uppercase">
                    </div>
                    
                    <div class="form-group col-md-8">
                        <label for="ra" class="col-form-label">Ra</label>
                        <input type="text" class="form-control descricao" id="ra" name="ra" required value="${aluno.ra}" style="text-transform: uppercase">
                    </div>
                    
                     <div class="form-group col-md-8">
                        <label for="login" class="col-form-label">Login</label>
                        <input type="text" class="form-control descricao" id="login" name="login" required value="${aluno.login}" style="text-transform: uppercase">
                    </div>
                    
                     <div class="form-group col-md-8">
                        <label for="senha" class="col-form-label">Senha</label>
                        <input type="password" class="form-control descricao" id="senha" name="senha" required value="${aluno.senha}" style="text-transform: uppercase">
                    </div>
                    
                     <div class="form-group col-md-8">
                        <label for="saldoads" class="col-form-label">Saldo</label>
                        <input type="text" class="form-control descricao" id="saldoads" name="saldoads" required value="${aluno.saldoAds}" style="text-transform: uppercase">
                    </div>
 
 
                <tr><td>
                        <input type="hidden" name="situacao" id="situacao" value="${aluno.situacao}" readonly="readonly"/>
                </td></tr>
                     
               </div>
                    <div class="form-group col-md-1">
                        <label class="col-form-label">Permite Login</label>
                        <select class="form-control descricao" name="permitelogin" id="permitelogin">
                            <option value="N" ${aluno.permiteLogin == 'N' ? "selected" : ""}>N?o</option>
                            <option value="S" ${aluno.permiteLogin == 'S' ? "selected" : ""}>Sim</option>       
                        </select>    
                    </div>
               <div class="form-group">
            <button  class="btn btn-success" type="submit"  id="submit" onclick="validarCampos()">Cadastrar</button>
                    <button type="reset" class="btn btn-danger navbar-btn" id="reset" onclick="limparCampos()"><i class="fa fa-floppy-o" aria-hidden="true"></i> Limpar</button>
            <button class="btn btn-warning">
                <a href="${pageContext.request.contextPath}/AlunoListar" class="btn-warning">Voltar</a>
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
        if (document.getElementById("nome").value === "") {
                        Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: 'Verifique o nome do aluno!',
                                showConfirmButton: false,
                                timer: 1000
                        });
                        $("#nome").focus();
        } else if (document.getElementById("ra").value === "") {
                        Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: 'Verifique o RA!',
                                showConfirmButton: false,
                                timer: 1000
                        });
                        $("#ra").focus();
        } else if (document.getElementById("login").value === '') {
                        Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: 'Verifique o login!',
                                showConfirmButton: false,
                                timer: 1000
                        });
                        $("#login").focus();
        }else if (document.getElementById("senha").value === '') {
                        Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: 'Verifique a senha!',
                                showConfirmButton: false,
                                timer: 1000
                        });
                        $("#senha").focus();
        }else if (document.getElementById("saldoads").value === '') {
                        Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: 'Verifique a senha!',
                                showConfirmButton: false,
                                timer: 1000
                        });
                        $("#saldoads").focus();
        }
        else {
                gravarDados();
        }
    }
     

        function gravarDados() {
        console.log("Gravando dados...");
                $.ajax({
                        type: 'post',
                        url: 'AlunoCadastrar',
                        data: {
                                idpessoa: $('#idpessoa').val(),
                                idaluno: $('#idaluno').val(),
                                ra: $('#ra').val(),
                                situacao: $('#situacao').val(),
                                permitelogin: $('#permitelogin').val(),
                                nome: $('#nome').val(),
                                login: $('#login').val(),
                                senha: $('#senha').val(),
                                saldoads: $('#saldoads').val()


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
                                                        text: 'Aluno gravado com sucesso!',
                                                        showConfirmButton: false,
                                                        timer: 1000
                                                });
                                        } else {
                                                Swal.fire({
                                                        position: 'center',
                                                        icon: 'error',
                                                        title: 'Erro',
                                                        text: 'Não foi possível gravar o aluno!',
                                                        showConfirmButton: false,
                                                        timer: 1000
                                                });
                                        }
                                        setTimeout(() => {window.location.href = "${pageContext.request.contextPath}/AlunoListar"}, 2000);
                                },
                        error:
                                function (data) {
                                        setTimeout(() => {window.location.href = "${pageContext.request.contextPath}/AlunoListar"}, 2000);
                                }
        });
    }
    function limparCampos() {
    $('#idaluno').val("");
    $('#permitelogin').val("");
    $('#situacao').val("");
    $('#idpessoa').val("");
    $('#ra').val("");
    $('#nome').val("");
    $('#login').val("");
    $('#senha').val("");
    $('#saldoads').val("");
}

function carregarPessoa(v) {
    var idM = v;
    var tipoPessoa = 'aluno';
    console.log("Usuario = " + idM);
    $(document).ready(function () {
        $.getJSON('PessoaBuscarCpf', {cpfcnpjpessoa: idM, tipopessoa: tipoPessoa}, function (respostaAluno) {
            console.log(respostaAluno);
            var id = respostaAluno.idAluno;
            if (respostaAluno != null)
            {
                $('#idaluno').val(respostaAluno.idAluno);
                $('#permitelogin').val(respostaAluno.permiteLogin);
                $('#situacao').val(respostaAluno.situacao);
                $('#idpessoa').val(respostaAluno.idPessoa);
                $('#nome').val(respostaAluno.nome);
                $('#login').val(respostaAluno.login);
                $('#senha').val(respostaAluno.senha);
                $('#ra').val(respostaAluno.ra);
                $('#saldoads').val(respostaAluno.saldoAds);

            } else {
                //se não encontrou administrador busca por pessoa somente
                tipoPessoa = 'pessoa';
                $.getJSON('PessoaBuscarCpf', {cpfcnpjpessoa: idM, tipopessoa: tipoPessoa}, function (respostaPessoa) {
                    console.log(respostaPessoa);
                    var id = respostaPessoa.idPessoa;
                    if (id != "0")
                    {
                        $('#idpessoa').val(respostaPessoa.idPessoa);
                        $('#cpf').val(respostaPessoa.cpf);
                        $('#nome').val(respostaPessoa.nome);
                        $('#senha').val(respostaPessoa.senha);
                        $('#login').val(respostaPessoa.login);
                    }
                });
            }
        });
    });
}
</script>       
<%@include file="/footer.jsp" %>

