package com.meets.meetsbackend.config;

import com.meets.meetsbackend.model.Comentario;
import com.meets.meetsbackend.model.Evento;
import com.meets.meetsbackend.model.Imagem;
import com.meets.meetsbackend.model.Postagem;
import com.meets.meetsbackend.model.Usuario;
import com.meets.meetsbackend.repository.ComentarioRepository;
import com.meets.meetsbackend.repository.EventoRepository;
import com.meets.meetsbackend.repository.ImagemRepository;
import com.meets.meetsbackend.repository.PostagemRepository;
import com.meets.meetsbackend.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Configuração para carregar dados iniciais de teste no banco de dados.
 * Baseado nos dados do mockPosts.json
 * 
 * Esta configuração só é executada quando o profile 'dev' ou 'test' estiver
 * ativo.
 * Para ativar: adicione spring.profiles.active=dev no application.properties
 */
@Configuration
@Profile({ "dev", "test" })
public class LoadDatabase {

        private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Bean
        CommandLineRunner initDatabase(
                        UsuarioRepository usuarioRepository,
                        PostagemRepository postagemRepository,
                        ComentarioRepository comentarioRepository,
                        EventoRepository eventoRepository,
                        ImagemRepository imagemRepository) {

                return args -> {
                        log.info("📦 Iniciando carregamento de dados de teste...");

                        // Limpar dados existentes (opcional - comentar se quiser manter dados)
                        comentarioRepository.deleteAll();
                        imagemRepository.deleteAll();
                        eventoRepository.deleteAll();
                        postagemRepository.deleteAll();
                        usuarioRepository.deleteAll();

                        // ========== CRIAR USUÁRIOS ==========
                        log.info("👥 Criando usuários...");

                        // Senha padrão para todos: "senha123"
                        // Hash BCrypt gerado:
                        // $2a$10$8K1p/o7UBP0wKz5/9oe0aOSvvqUn4Cqq5Y5b0NjF.kPQq0j2J6JmW
                        String senha = passwordEncoder.encode("senha123");

                        Usuario joao = usuarioRepository.save(new Usuario(
                                        "João Silva",
                                        "joao@fatec.com",
                                        senha,
                                        "Desenvolvedor Full Stack apaixonado por tecnologia e inovação. Sempre em busca de novos desafios! 🚀"));
                        joao.setFotoPerfil("https://i.pravatar.cc/150?img=1");
                        joao = usuarioRepository.save(joao);

                        Usuario maria = usuarioRepository.save(new Usuario(
                                        "Maria Santos",
                                        "maria@fatec.com",
                                        senha,
                                        "Organizadora de eventos tech | Entusiasta de React Native | Amo conectar pessoas através da tecnologia 💻✨"));
                        maria.setFotoPerfil("https://i.pravatar.cc/150?img=5");
                        maria = usuarioRepository.save(maria);

                        Usuario pedro = usuarioRepository.save(new Usuario(
                                        "Pedro Lima",
                                        "pedro@fatec.com",
                                        senha,
                                        "Estudante de ADS | Finalizando TCC | Futuro engenheiro de software em construção 🎓💙"));
                        pedro.setFotoPerfil("https://i.pravatar.cc/150?img=8");
                        pedro = usuarioRepository.save(pedro);

                        Usuario ana = usuarioRepository.save(new Usuario(
                                        "Ana Costa",
                                        "ana@fatec.com",
                                        senha,
                                        "Mobile Developer | React Native expert | Compartilhando conhecimento e aprendendo sempre 📱⚡"));
                        ana.setFotoPerfil("https://i.pravatar.cc/150?img=9");
                        ana = usuarioRepository.save(ana);

                        Usuario carlos = usuarioRepository.save(new Usuario(
                                        "Carlos Mendes",
                                        "carlos@fatec.com",
                                        senha,
                                        "Cloud enthusiast ☁️ | Palestrante | Ajudando desenvolvedores a migrarem para a nuvem"));
                        carlos.setFotoPerfil("https://i.pravatar.cc/150?img=12");
                        carlos = usuarioRepository.save(carlos);

                        Usuario julia = usuarioRepository.save(new Usuario(
                                        "Julia Fernandes",
                                        "julia@fatec.com",
                                        senha,
                                        "Tech lover | Early adopter | Sempre testando as últimas tecnologias do mercado 🔥"));
                        julia.setFotoPerfil("https://i.pravatar.cc/150?img=20");
                        julia = usuarioRepository.save(julia);

                        Usuario lucas = usuarioRepository.save(new Usuario(
                                        "Lucas Almeida",
                                        "lucas@fatec.com",
                                        senha,
                                        "Android Developer | Problem solver | Se tiver bug, eu resolvo! 🛠️ #CodigoLimpo"));
                        lucas.setFotoPerfil("https://i.pravatar.cc/150?img=15");
                        lucas = usuarioRepository.save(lucas);

                        Usuario fernanda = usuarioRepository.save(new Usuario(
                                        "Fernanda Rocha",
                                        "fernanda@fatec.com",
                                        senha,
                                        "Game Developer 🎮 | Unity & Unreal | Transformando ideias em experiências interativas incríveis"));
                        fernanda.setFotoPerfil("https://i.pravatar.cc/150?img=25");
                        fernanda = usuarioRepository.save(fernanda);

                        Usuario rafael = usuarioRepository.save(new Usuario(
                                        "Rafael Santos",
                                        "rafael@fatec.com",
                                        senha,
                                        "Indie Developer | Meu primeiro app na Play Store! 📲 | Realizando sonhos uma linha de código por vez"));
                        rafael.setFotoPerfil("https://i.pravatar.cc/150?img=33");
                        rafael = usuarioRepository.save(rafael);

                        Usuario beatriz = usuarioRepository.save(new Usuario(
                                        "Beatriz Lima",
                                        "beatriz@fatec.com",
                                        senha,
                                        "DevOps Engineer | Git & GitHub specialist | Automatizando processos e facilitando a vida dos devs 💻🚀"));
                        beatriz.setFotoPerfil("https://i.pravatar.cc/150?img=47");
                        beatriz = usuarioRepository.save(beatriz);

                        log.info("✅ {} usuários criados", usuarioRepository.count());
                        log.info("📧 Todos os emails simplificados para: nome@fatec.com");
                        log.info("🔑 Senha padrão para todos: senha123");
                        log.info("📸 Fotos de perfil adicionadas para todos os usuários");

                        // ========== CRIAR POSTAGENS ==========
                        log.info("📝 Criando postagens...");

                        // Post 1 - Beatriz (COM EVENTO - Workshop Git) - 1 imagem - MAIS RECENTE
                        Postagem post1 = new Postagem(
                                        "EVENTO: Workshop de Git e GitHub",
                                        "🚀 EVENTO: Workshop de Git e GitHub\n\nNível: Iniciante ao Avançado\nData: 08/11/2025 das 14h às 18h\nInstrutor: Prof. Marcelo Oliveira\n\nVagas limitadas! Inscreva-se já! �",
                                        beatriz);
                        post1.setDataCriacao(LocalDateTime.of(2025, 11, 4, 16, 30));
                        post1 = postagemRepository.save(post1);
                        imagemRepository.save(
                                        new Imagem("https://picsum.photos/700/400?random=12",
                                                        "Banner Workshop Git e GitHub", post1, 0));

                        // Post 2 - Lucas (sem evento) - SEM imagem
                        Postagem post2 = new Postagem(
                                        "Problemas com Android Build",
                                        "Alguém mais teve problemas com o build do Android hoje? Consegui resolver limpando o cache do Gradle. Espero que ajude! �️",
                                        lucas);
                        post2.setDataCriacao(LocalDateTime.of(2025, 11, 4, 11, 0));
                        post2 = postagemRepository.save(post2);

                        // Post 3 - Rafael (sem evento) - 3 imagens
                        Postagem post3 = new Postagem(
                                        "Primeiro App Publicado",
                                        "Acabei de publicar meu primeiro app na Play Store! Depois de meses desenvolvendo, finalmente está no ar. Link na bio! �🎉",
                                        rafael);
                        post3.setDataCriacao(LocalDateTime.of(2025, 11, 3, 20, 0));
                        post3 = postagemRepository.save(post3);
                        imagemRepository
                                        .save(new Imagem("https://picsum.photos/400/800?random=9",
                                                        "Screenshot App - Tela 1", post3, 0));
                        imagemRepository
                                        .save(new Imagem("https://picsum.photos/400/800?random=10",
                                                        "Screenshot App - Tela 2", post3, 1));
                        imagemRepository
                                        .save(new Imagem("https://picsum.photos/400/800?random=11",
                                                        "Screenshot App - Tela 3", post3, 2));

                        // Post 4 - Pedro (sem evento) - 2 imagens
                        Postagem post4 = new Postagem(
                                        "Projeto TCC Finalizado",
                                        "Finalmente terminei meu projeto de TCC! 6 meses de muito trabalho, mas valeu cada linha de código. Obrigado a todos que me ajudaram! 🎓💙",
                                        pedro);
                        post4.setDataCriacao(LocalDateTime.of(2025, 11, 3, 8, 15));
                        post4 = postagemRepository.save(post4);
                        imagemRepository
                                        .save(new Imagem("https://picsum.photos/700/500?random=3",
                                                        "Projeto TCC - Tela 1", post4, 0));
                        imagemRepository
                                        .save(new Imagem("https://picsum.photos/700/500?random=4",
                                                        "Projeto TCC - Tela 2", post4, 1));

                        // Post 5 - Fernanda (COM EVENTO - Game Jam) - 2 imagens
                        Postagem post5 = new Postagem(
                                        "EVENTO: Game Jam Fatec",
                                        "🎮 EVENTO: Game Jam Fatec\n\nCrie um jogo em 48 horas!\nData: 20-22/11/2025\nPrêmios para os 3 melhores jogos!\n\nInscrições até 15/11. Quem topa o desafio? �️",
                                        fernanda);
                        post5.setDataCriacao(LocalDateTime.of(2025, 11, 2, 18, 45));
                        post5 = postagemRepository.save(post5);
                        imagemRepository
                                        .save(new Imagem("https://picsum.photos/900/600?random=7",
                                                        "Banner Game Jam Fatec", post5, 0));
                        imagemRepository.save(new Imagem("https://picsum.photos/900/600?random=8", "Prêmios Game Jam",
                                        post5, 1));

                        // Post 6 - Ana (sem evento) - SEM imagem
                        Postagem post6 = new Postagem(
                                        "Dica React Native",
                                        "Dica rápida de React Native: Use o useMemo e useCallback para otimizar a performance da sua aplicação! Fez toda diferença no meu app. �⚡",
                                        ana);
                        post6.setDataCriacao(LocalDateTime.of(2025, 11, 2, 16, 30));
                        post6 = postagemRepository.save(post6);

                        // Post 7 - João (sem evento) - 1 imagem
                        Postagem post7 = new Postagem(
                                        "Feira de Tecnologia 2025",
                                        "Acabei de voltar da Feira de Tecnologia 2025! Foi incrível ver tantas inovações em IA e desenvolvimento mobile. Quem mais esteve lá? �",
                                        joao);
                        post7.setDataCriacao(LocalDateTime.of(2025, 11, 2, 14, 30));
                        post7 = postagemRepository.save(post7);
                        imagemRepository
                                        .save(new Imagem("https://picsum.photos/600/400?random=1",
                                                        "Feira de Tecnologia 2025", post7, 0));

                        // Post 8 - Maria (COM EVENTO - Hackathon) - 1 imagem
                        Postagem post8 = new Postagem(
                                        "EVENTO: Hackathon Fatec 2025",
                                        "📅 EVENTO: Hackathon Fatec 2025\n\nQuando: 15-17 de Novembro\nOnde: Campus Fatec São Paulo\n\nInscrições abertas! Prêmios incríveis para os vencedores. Quem vai participar? ��",
                                        maria);
                        post8.setDataCriacao(LocalDateTime.of(2025, 11, 2, 10, 0));
                        post8 = postagemRepository.save(post8);
                        imagemRepository.save(
                                        new Imagem("https://picsum.photos/800/600?random=2",
                                                        "Banner do Hackathon Fatec 2025", post8, 0));

                        // Post 9 - Julia (sem evento) - 1 imagem
                        Postagem post9 = new Postagem(
                                        "Novo Framework",
                                        "Quem mais está animado para o lançamento do novo framework? A comunidade está em polvorosa! 🔥",
                                        julia);
                        post9.setDataCriacao(LocalDateTime.of(2025, 11, 1, 19, 30));
                        post9 = postagemRepository.save(post9);
                        imagemRepository.save(new Imagem("https://picsum.photos/500/500?random=6", "Novo Framework",
                                        post9, 0));

                        // Post 10 - Carlos (COM EVENTO - Palestra Cloud) - 1 imagem - MAIS ANTIGO
                        Postagem post10 = new Postagem(
                                        "EVENTO: Palestra sobre Cloud Computing",
                                        "🎉 EVENTO: Palestra sobre Cloud Computing\n\nPalestrante: Dr. Roberto Tavares\nData: 10/11/2025 às 19h\nLocal: Auditório Principal\n\nGratuito! Não percam! ☁️",
                                        carlos);
                        post10.setDataCriacao(LocalDateTime.of(2025, 11, 1, 9, 0));
                        post10 = postagemRepository.save(post10);
                        imagemRepository.save(
                                        new Imagem("https://picsum.photos/600/800?random=5",
                                                        "Banner Palestra Cloud Computing", post10, 0));

                        // Evento será criado após adicionar os likes no post

                        log.info("✅ {} postagens criadas", postagemRepository.count());
                        log.info("📸 {} imagens criadas no total", imagemRepository.count());

                        // ========== ADICIONAR LIKES NAS POSTAGENS ==========
                        log.info("❤️ Adicionando likes nas postagens...");

                        // Post 1 - Beatriz (Workshop Git): 7 likes
                        post1.adicionarLike(joao);
                        post1.adicionarLike(pedro);
                        post1.adicionarLike(ana);
                        post1.adicionarLike(carlos);
                        post1.adicionarLike(julia);
                        post1.adicionarLike(lucas);
                        post1.adicionarLike(rafael);
                        postagemRepository.save(post1);

                        // Post 2 - Lucas (Android Build): 2 likes
                        post2.adicionarLike(pedro);
                        post2.adicionarLike(rafael);
                        postagemRepository.save(post2);

                        // Post 3 - Rafael (Primeiro App): 6 likes
                        post3.adicionarLike(joao);
                        post3.adicionarLike(maria);
                        post3.adicionarLike(ana);
                        post3.adicionarLike(lucas);
                        post3.adicionarLike(fernanda);
                        post3.adicionarLike(beatriz);
                        postagemRepository.save(post3);

                        // Post 4 - Pedro (TCC): 7 likes
                        post4.adicionarLike(joao);
                        post4.adicionarLike(maria);
                        post4.adicionarLike(ana);
                        post4.adicionarLike(carlos);
                        post4.adicionarLike(lucas);
                        post4.adicionarLike(beatriz);
                        post4.adicionarLike(julia);
                        postagemRepository.save(post4);

                        // Post 5 - Fernanda (Game Jam): 9 likes (evento muito popular)
                        post5.adicionarLike(joao);
                        post5.adicionarLike(maria);
                        post5.adicionarLike(pedro);
                        post5.adicionarLike(ana);
                        post5.adicionarLike(carlos);
                        post5.adicionarLike(julia);
                        post5.adicionarLike(lucas);
                        post5.adicionarLike(rafael);
                        post5.adicionarLike(beatriz);
                        postagemRepository.save(post5);

                        // Post 6 - Ana (Dica RN): 4 likes
                        post6.adicionarLike(joao);
                        post6.adicionarLike(maria);
                        post6.adicionarLike(rafael);
                        post6.adicionarLike(lucas);
                        postagemRepository.save(post6);

                        // Post 7 - João: 5 likes
                        post7.adicionarLike(maria);
                        post7.adicionarLike(pedro);
                        post7.adicionarLike(ana);
                        post7.adicionarLike(carlos);
                        post7.adicionarLike(julia);
                        postagemRepository.save(post7);

                        // Post 8 - Maria (Hackathon): 8 likes (evento popular)
                        post8.adicionarLike(joao);
                        post8.adicionarLike(pedro);
                        post8.adicionarLike(ana);
                        post8.adicionarLike(carlos);
                        post8.adicionarLike(julia);
                        post8.adicionarLike(lucas);
                        post8.adicionarLike(fernanda);
                        post8.adicionarLike(rafael);
                        postagemRepository.save(post8);

                        // Post 9 - Julia (Novo Framework): 3 likes
                        post9.adicionarLike(ana);
                        post9.adicionarLike(lucas);
                        post9.adicionarLike(fernanda);
                        postagemRepository.save(post9);

                        // Post 10 - Carlos (Palestra Cloud): 6 likes
                        post10.adicionarLike(joao);
                        post10.adicionarLike(maria);
                        post10.adicionarLike(ana);
                        post10.adicionarLike(pedro);
                        post10.adicionarLike(beatriz);
                        post10.adicionarLike(julia);
                        postagemRepository.save(post10);

                        log.info("✅ Likes adicionados em todas as postagens");
                        log.info("❤️ Post mais curtido: Game Jam Fatec (9 likes)");
                        log.info("❤️ Total de likes distribuídos: 57");

                        // ========== CRIAR EVENTOS ==========
                        log.info("📅 Criando eventos...");

                        // Evento 1 - Workshop Git (vinculado ao post 1)
                        Evento evento1 = new Evento(
                                        "Workshop de Git e GitHub",
                                        "Aprenda a usar Git e GitHub como um profissional! Do básico ao avançado: commits, branches, pull requests, resolução de conflitos e muito mais.",
                                        java.time.LocalDate.of(2025, 11, 8),
                                        java.time.LocalTime.of(14, 0),
                                        java.time.LocalTime.of(18, 0),
                                        "Laboratório 3 - Fatec",
                                        "Campus Fatec - Lab 3 (2º andar)",
                                        40,
                                        beatriz,
                                        post1);
                        evento1.adicionarParticipante(joao);
                        evento1.adicionarParticipante(maria);
                        evento1.adicionarParticipante(carlos);
                        eventoRepository.save(evento1);

                        // Evento 2 - Game Jam Fatec (vinculado ao post 5)
                        Evento evento2 = new Evento(
                                        "Game Jam Fatec 2025",
                                        "48 horas para criar um jogo incrível! Evento para desenvolvedores de games de todos os níveis. Tema será revelado no início!",
                                        java.time.LocalDate.of(2025, 11, 20),
                                        java.time.LocalTime.of(18, 0),
                                        java.time.LocalTime.of(18, 0), // Termina 2 dias depois
                                        "Laboratórios de Informática - Fatec",
                                        "Campus Fatec - Labs 1, 2 e 3",
                                        60,
                                        fernanda,
                                        post5);
                        evento2.adicionarParticipante(rafael);
                        evento2.adicionarParticipante(lucas);
                        evento2.adicionarParticipante(ana);
                        evento2.adicionarParticipante(pedro);
                        eventoRepository.save(evento2);

                        // Evento 3 - Hackathon Fatec 2025 (vinculado ao post 8)
                        Evento evento3 = new Evento(
                                        "Hackathon Fatec 2025",
                                        "Participe do maior evento de programação da Fatec! 24 horas de muito código, networking e prêmios incríveis. Traga sua equipe ou forme uma no local!",
                                        java.time.LocalDate.of(2025, 11, 15),
                                        java.time.LocalTime.of(8, 0),
                                        java.time.LocalTime.of(20, 0),
                                        "Campus Fatec São Paulo",
                                        "Av. Tiradentes, 615 - Bom Retiro, São Paulo - SP",
                                        80,
                                        maria,
                                        post8);
                        evento3.adicionarParticipante(joao);
                        evento3.adicionarParticipante(pedro);
                        evento3.adicionarParticipante(ana);
                        evento3.adicionarParticipante(lucas);
                        evento3.adicionarParticipante(fernanda);
                        eventoRepository.save(evento3);

                        // Evento 4 - Palestra Cloud Computing (vinculado ao post 10)
                        Evento evento4 = new Evento(
                                        "Palestra sobre Cloud Computing",
                                        "Descubra as melhores práticas em computação em nuvem com especialistas da área. Aprenda sobre AWS, Azure e Google Cloud Platform.",
                                        java.time.LocalDate.of(2025, 11, 10),
                                        java.time.LocalTime.of(19, 0),
                                        java.time.LocalTime.of(21, 30),
                                        "Auditório Principal - Fatec",
                                        "Av. Tiradentes, 615 - Auditório 1º andar",
                                        150,
                                        carlos,
                                        post10);
                        evento4.adicionarParticipante(joao);
                        evento4.adicionarParticipante(maria);
                        evento4.adicionarParticipante(pedro);
                        eventoRepository.save(evento4);

                        log.info("✅ {} eventos criados", eventoRepository.count());
                        log.info("📸 Eventos compartilham as imagens dos posts vinculados");
                        log.info("👥 Participantes adicionados aos eventos");

                        // ========== CRIAR COMENTÁRIOS ==========
                        log.info("💬 Criando comentários...");

                        // Comentários do Post 1 (Workshop Git)
                        Comentario c1 = new Comentario(
                                        "Perfeito! Estava precisando reforçar Git",
                                        post1,
                                        maria);
                        c1.setDataComentario(LocalDateTime.of(2025, 11, 4, 17, 0));
                        comentarioRepository.save(c1);

                        Comentario c2 = new Comentario(
                                        "Já me inscrevi! O professor é ótimo! 👨‍🏫",
                                        post1,
                                        lucas);
                        c2.setDataComentario(LocalDateTime.of(2025, 11, 4, 18, 0));
                        comentarioRepository.save(c2);

                        Comentario c3 = new Comentario(
                                        "Ainda tem vagas disponíveis?",
                                        post1,
                                        carlos);
                        c3.setDataComentario(LocalDateTime.of(2025, 11, 4, 19, 30));
                        comentarioRepository.save(c3);

                        // Comentários do Post 2 (Android Build)
                        Comentario c4 = new Comentario(
                                        "Salvou meu dia! Obrigado!!!",
                                        post2,
                                        carlos);
                        c4.setDataComentario(LocalDateTime.of(2025, 11, 4, 11, 30));
                        comentarioRepository.save(c4);

                        Comentario c5 = new Comentario(
                                        "Funcionou aqui também! Valeu demais 🙏",
                                        post2,
                                        beatriz);
                        c5.setDataComentario(LocalDateTime.of(2025, 11, 4, 12, 0));
                        comentarioRepository.save(c5);

                        // Comentários do Post 3 (App Play Store)
                        Comentario c6 = new Comentario(
                                        "Parabéns mano! Acabei de baixar, ficou top! 🔥",
                                        post3,
                                        joao);
                        c6.setDataComentario(LocalDateTime.of(2025, 11, 3, 20, 30));
                        comentarioRepository.save(c6);

                        Comentario c7 = new Comentario(
                                        "Que orgulho! Seu app é incrível! �",
                                        post3,
                                        ana);
                        c7.setDataComentario(LocalDateTime.of(2025, 11, 3, 21, 15));
                        comentarioRepository.save(c7);

                        Comentario c8 = new Comentario(
                                        "Já instalei e avaliei 5 estrelas! Sucesso! ⭐",
                                        post3,
                                        fernanda);
                        c8.setDataComentario(LocalDateTime.of(2025, 11, 4, 8, 0));
                        comentarioRepository.save(c8);

                        Comentario c9 = new Comentario(
                                        "UI/UX ficou perfeita! Parabéns pelo trabalho!",
                                        post3,
                                        beatriz);
                        c9.setDataComentario(LocalDateTime.of(2025, 11, 4, 10, 30));
                        comentarioRepository.save(c9);

                        // Comentários do Post 4 (TCC)
                        Comentario c10 = new Comentario(
                                        "Parabéns! Seu projeto ficou incrível!",
                                        post4,
                                        maria);
                        c10.setDataComentario(LocalDateTime.of(2025, 11, 3, 8, 45));
                        comentarioRepository.save(c10);

                        Comentario c11 = new Comentario(
                                        "Merecido! Você se dedicou muito 👏",
                                        post4,
                                        julia);
                        c11.setDataComentario(LocalDateTime.of(2025, 11, 3, 9, 30));
                        comentarioRepository.save(c11);

                        // Comentários do Post 5 (Game Jam)
                        Comentario c12 = new Comentario(
                                        "Meu time já está pronto! Vamos arrasar! 🎯",
                                        post5,
                                        julia);
                        c12.setDataComentario(LocalDateTime.of(2025, 11, 2, 19, 0));
                        comentarioRepository.save(c12);

                        Comentario c13 = new Comentario(
                                        "Primeira vez que vou participar, animado!",
                                        post5,
                                        pedro);
                        c13.setDataComentario(LocalDateTime.of(2025, 11, 2, 20, 0));
                        comentarioRepository.save(c13);

                        Comentario c14 = new Comentario(
                                        "Alguém precisa de um programador Unity?",
                                        post5,
                                        lucas);
                        c14.setDataComentario(LocalDateTime.of(2025, 11, 3, 14, 30));
                        comentarioRepository.save(c14);

                        Comentario c15 = new Comentario(
                                        "Vou fazer um jogo mobile! Quem se junta?",
                                        post5,
                                        rafael);
                        c15.setDataComentario(LocalDateTime.of(2025, 11, 3, 10, 0));
                        comentarioRepository.save(c15);

                        // Comentários do Post 6 (React Native)
                        Comentario c16 = new Comentario(
                                        "Muito útil! Estava precisando dessa dica",
                                        post6,
                                        lucas);
                        c16.setDataComentario(LocalDateTime.of(2025, 11, 2, 17, 0));
                        comentarioRepository.save(c16);

                        Comentario c17 = new Comentario(
                                        "Salvando esse post! Obrigada por compartilhar 💜",
                                        post6,
                                        fernanda);
                        c17.setDataComentario(LocalDateTime.of(2025, 11, 2, 18, 15));
                        comentarioRepository.save(c17);

                        Comentario c18 = new Comentario(
                                        "useCallback salvou meu projeto! Ótima dica",
                                        post6,
                                        pedro);
                        c18.setDataComentario(LocalDateTime.of(2025, 11, 3, 10, 0));
                        comentarioRepository.save(c18);

                        // Comentários do Post 7 (Feira de Tecnologia)
                        Comentario c19 = new Comentario(
                                        "Eu fui! A palestra sobre React Native foi sensacional!",
                                        post7,
                                        maria);
                        c19.setDataComentario(LocalDateTime.of(2025, 11, 2, 15, 45));
                        comentarioRepository.save(c19);

                        Comentario c20 = new Comentario(
                                        "Queria ter ido... ano que vem não perco!",
                                        post7,
                                        pedro);
                        c20.setDataComentario(LocalDateTime.of(2025, 11, 2, 16, 20));
                        comentarioRepository.save(c20);

                        // Comentários do Post 8 (Hackathon)
                        Comentario c21 = new Comentario(
                                        "Já me inscrevi! Vai ser épico!",
                                        post8,
                                        joao);
                        c21.setDataComentario(LocalDateTime.of(2025, 11, 2, 11, 30));
                        comentarioRepository.save(c21);

                        Comentario c22 = new Comentario(
                                        "Meu time já está formado! �",
                                        post8,
                                        ana);
                        c22.setDataComentario(LocalDateTime.of(2025, 11, 2, 14, 15));
                        comentarioRepository.save(c22);

                        Comentario c23 = new Comentario(
                                        "Alguém precisa de mais um dev? Topo participar!",
                                        post8,
                                        carlos);
                        c23.setDataComentario(LocalDateTime.of(2025, 11, 3, 9, 0));
                        comentarioRepository.save(c23);

                        // Comentários do Post 9 (Framework)
                        Comentario c24 = new Comentario(
                                        "Mal posso esperar! As features anunciadas são incríveis",
                                        post9,
                                        maria);
                        c24.setDataComentario(LocalDateTime.of(2025, 11, 1, 20, 45));
                        comentarioRepository.save(c24);

                        Comentario c25 = new Comentario(
                                        "Já comecei a estudar a documentação! 📚",
                                        post9,
                                        ana);
                        c25.setDataComentario(LocalDateTime.of(2025, 11, 2, 8, 30));
                        comentarioRepository.save(c25);

                        Comentario c26 = new Comentario(
                                        "Vai revolucionar o desenvolvimento!",
                                        post9,
                                        rafael);
                        c26.setDataComentario(LocalDateTime.of(2025, 11, 2, 11, 15));
                        comentarioRepository.save(c26);

                        // Comentários do Post 10 (Cloud)
                        Comentario c27 = new Comentario(
                                        "Já marquei na agenda!",
                                        post10,
                                        joao);
                        c27.setDataComentario(LocalDateTime.of(2025, 11, 1, 10, 30));
                        comentarioRepository.save(c27);

                        log.info("✅ {} comentários criados", comentarioRepository.count());

                        log.info("🎉 Carregamento de dados de teste concluído!");
                        log.info("📊 Resumo:");
                        log.info("   - Usuários: {}", usuarioRepository.count());
                        log.info("   - Postagens: {}", postagemRepository.count());
                        log.info("   - Eventos: {}", eventoRepository.count());
                        log.info("   - Comentários: {}", comentarioRepository.count());
                };
        }
}
