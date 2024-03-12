create table pessoa (
	idpessoa serial primary key,
	nome varchar(100) not null,
	cpf varchar(14) not null unique,
	login varchar(100) not null,
	senha varchar(128) not null
);
insert into pessoa(nome, cpf, login, senha) values ('Alexandre Bernardes', '49509738000', 'xexe', '123456');

create table aluno(
	idaluno serial primary key,
	idpessoa int unique, 
	ra bigint not null unique,
	saldoads numeric(4,2),
	situacao varchar(1) not null,
	permitelogin varchar(1) not null,
	constraint fk_aluno_pessoa foreign key (idpessoa) references pessoa
);

create table administrador(
    idadministrador serial primary key,
    idpessoa int unique,
    situacao varchar(1) not null,
    permitelogin varchar(1) not null,
	constraint fk_administrador_pessoa foreign key (idpessoa) references pessoa
);

insert into administrador (idpessoa, situacao, permitelogin) values (1, 'A', 'S');
create table semestre (
	idsemestre serial primary key,
	numsemestre varchar(20),
	situacao varchar(1) not null
);

insert into semestre(numsemestre, situacao) values ('Primeiro Semestre', 'A');

create table professor(
	idprofessor serial primary key,
	idpessoa int unique,
	emailprofessor varchar(50) not null,
	formacaoprofessor varchar(30) not null,
	situacao varchar(1) not null,
	permitelogin varchar(1) not null,
	constraint fk_professor_pessoa foreign key (idpessoa) references pessoa
);

create table disciplina(
    iddisciplina serial primary key,
    nomedisciplina varchar(100) not null,
    situacao varchar(1) not null,
    idsemestre int not null,
    constraint fk_semestre foreign key (idsemestre) references semestre(idsemestre)
);

insert into disciplina (nomedisciplina,situacao,idsemestre) values ('Estrutura de Dados', 'A', 1);

create table atividade(
	idatividade serial primary key,
	descricao varchar(100) not null,
	situacao varchar(1) not null,
	status varchar(1) not null,
	dataatividade date not null,
	dataprazo date not null,
	documento text not null, 
	iddisciplina int not null,
	pontuacaomax int not null,
	constraint fk_atividade foreign key (iddisciplina) references disciplina(iddisciplina)
);


create or replace view usuario as 
select p.idpessoa, p.nome, p.cpf, p.login, p.senha, pr.idprofessor as id, 'Professor' as tipo from pessoa p, professor pr
where pr.idpessoa = p.idpessoa and pr.situacao = 'A' and pr.permitelogin = 'S' 
union 
select p.idpessoa, p.nome, p.cpf, p.login, p.senha, a.idadministrador as id, 'Administrador' as tipo from pessoa p, administrador a 
where a.idpessoa = p.idpessoa and a.situacao = 'A' and a.permitelogin = 'S'
union
select p.idpessoa, p.nome, p.cpf, p.login, p.senha, al.idaluno as id, 'Aluno' as tipo from pessoa p, aluno al 
where al.idpessoa = p.idpessoa and al.situacao = 'A' and al.permitelogin = 'S';
