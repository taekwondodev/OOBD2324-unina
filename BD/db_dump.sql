--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1
-- Dumped by pg_dump version 16.2 (Postgres.app)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'LATIN1';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: savingmoneyunina_schema; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA savingmoneyunina_schema;


ALTER SCHEMA savingmoneyunina_schema OWNER TO postgres;

--
-- Name: adminpack; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION adminpack; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';


--
-- Name: enum_categoria; Type: DOMAIN; Schema: savingmoneyunina_schema; Owner: postgres
--

CREATE DOMAIN savingmoneyunina_schema.enum_categoria AS character varying(13)
	CONSTRAINT check_categoria CHECK (((VALUE)::text = ANY ((ARRAY['Svago'::character varying, 'Spese_mediche'::character varying, 'Stipendio'::character varying, 'Bollette'::character varying])::text[])));


ALTER DOMAIN savingmoneyunina_schema.enum_categoria OWNER TO postgres;

--
-- Name: enum_tipo_carta; Type: DOMAIN; Schema: savingmoneyunina_schema; Owner: postgres
--

CREATE DOMAIN savingmoneyunina_schema.enum_tipo_carta AS character varying(7)
	CONSTRAINT check_tipo_carta CHECK (((VALUE)::text = ANY ((ARRAY['Debito'::character varying, 'Credito'::character varying])::text[])));


ALTER DOMAIN savingmoneyunina_schema.enum_tipo_carta OWNER TO postgres;

--
-- Name: enum_tipo_transazione; Type: DOMAIN; Schema: savingmoneyunina_schema; Owner: postgres
--

CREATE DOMAIN savingmoneyunina_schema.enum_tipo_transazione AS character varying(7)
	CONSTRAINT check_tipo_transazione CHECK (((VALUE)::text = ANY ((ARRAY['Entrata'::character varying, 'Uscita'::character varying])::text[])));


ALTER DOMAIN savingmoneyunina_schema.enum_tipo_transazione OWNER TO postgres;

--
-- Name: tipo_cvv; Type: DOMAIN; Schema: savingmoneyunina_schema; Owner: postgres
--

CREATE DOMAIN savingmoneyunina_schema.tipo_cvv AS character varying(4)
	CONSTRAINT check_cvv CHECK (((length((VALUE)::text) = 3) OR (length((VALUE)::text) = 4)));


ALTER DOMAIN savingmoneyunina_schema.tipo_cvv OWNER TO postgres;

--
-- Name: tipo_numero_carta; Type: DOMAIN; Schema: savingmoneyunina_schema; Owner: postgres
--

CREATE DOMAIN savingmoneyunina_schema.tipo_numero_carta AS character(16)
	CONSTRAINT check_numero_carta CHECK ((length(VALUE) = 16));


ALTER DOMAIN savingmoneyunina_schema.tipo_numero_carta OWNER TO postgres;

--
-- Name: aggiorna_saldo(); Type: FUNCTION; Schema: savingmoneyunina_schema; Owner: postgres
--

CREATE FUNCTION savingmoneyunina_schema.aggiorna_saldo() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
     IF (NEW.tipo_transazione = 'Entrata') THEN
	    UPDATE Carta
		SET saldo = saldo + NEW.importo
		WHERE numero = NEW.numero_carta;
	ELSIF (NEW.tipo_transazione = 'Uscita') THEN
	    UPDATE Carta
		SET saldo = saldo - NEW.importo
		WHERE numero = NEW.numero_carta;
	END IF;
	RETURN NEW;
END;
$$;


ALTER FUNCTION savingmoneyunina_schema.aggiorna_saldo() OWNER TO postgres;

--
-- Name: inserisci_famiglia_aggiorna_utente(character varying, character varying); Type: PROCEDURE; Schema: savingmoneyunina_schema; Owner: postgres
--

CREATE PROCEDURE savingmoneyunina_schema.inserisci_famiglia_aggiorna_utente(IN nome_famiglia character varying, IN n_utente character varying)
    LANGUAGE plpgsql
    AS $$
DECLARE
       current_id_famiglia INT;
BEGIN
       INSERT INTO Famiglia(nome) VALUES(nome_famiglia)
	   RETURNING id_famiglia INTO current_id_famiglia;
	   
	   UPDATE Utente SET id_famiglia = current_id_famiglia
	   WHERE nome_utente = n_utente;
END;
$$;


ALTER PROCEDURE savingmoneyunina_schema.inserisci_famiglia_aggiorna_utente(IN nome_famiglia character varying, IN n_utente character varying) OWNER TO postgres;

--
-- Name: verifica_saldo(); Type: FUNCTION; Schema: savingmoneyunina_schema; Owner: postgres
--

CREATE FUNCTION savingmoneyunina_schema.verifica_saldo() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
       current_saldo DECIMAL(10, 2);
BEGIN
     SELECT saldo INTO current_saldo
	 FROM Carta
	 WHERE numero = NEW.numero_carta;
	 
	 IF NEW.tipo_transazione = 'Uscita' AND NEW.importo > current_saldo THEN
	       RAISE EXCEPTION 'Saldo insufficiente';
	 END IF;
	 RETURN NEW;
END;
$$;


ALTER FUNCTION savingmoneyunina_schema.verifica_saldo() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: carta; Type: TABLE; Schema: savingmoneyunina_schema; Owner: postgres
--

CREATE TABLE savingmoneyunina_schema.carta (
    numero savingmoneyunina_schema.tipo_numero_carta NOT NULL,
    cvv savingmoneyunina_schema.tipo_cvv NOT NULL,
    scadenza date NOT NULL,
    tipo_carta savingmoneyunina_schema.enum_tipo_carta,
    saldo numeric(10,2),
    id_utente integer NOT NULL
);


ALTER TABLE savingmoneyunina_schema.carta OWNER TO postgres;

--
-- Name: famiglia; Type: TABLE; Schema: savingmoneyunina_schema; Owner: postgres
--

CREATE TABLE savingmoneyunina_schema.famiglia (
    id_famiglia integer NOT NULL,
    nome character varying(20)
);


ALTER TABLE savingmoneyunina_schema.famiglia OWNER TO postgres;

--
-- Name: famiglia_id_famiglia_seq; Type: SEQUENCE; Schema: savingmoneyunina_schema; Owner: postgres
--

CREATE SEQUENCE savingmoneyunina_schema.famiglia_id_famiglia_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE savingmoneyunina_schema.famiglia_id_famiglia_seq OWNER TO postgres;

--
-- Name: famiglia_id_famiglia_seq; Type: SEQUENCE OWNED BY; Schema: savingmoneyunina_schema; Owner: postgres
--

ALTER SEQUENCE savingmoneyunina_schema.famiglia_id_famiglia_seq OWNED BY savingmoneyunina_schema.famiglia.id_famiglia;


--
-- Name: gruppo; Type: TABLE; Schema: savingmoneyunina_schema; Owner: postgres
--

CREATE TABLE savingmoneyunina_schema.gruppo (
    id_gruppo integer NOT NULL,
    categoria savingmoneyunina_schema.enum_categoria NOT NULL,
    nome character varying(20)
);


ALTER TABLE savingmoneyunina_schema.gruppo OWNER TO postgres;

--
-- Name: gruppo_id_gruppo_seq; Type: SEQUENCE; Schema: savingmoneyunina_schema; Owner: postgres
--

CREATE SEQUENCE savingmoneyunina_schema.gruppo_id_gruppo_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE savingmoneyunina_schema.gruppo_id_gruppo_seq OWNER TO postgres;

--
-- Name: gruppo_id_gruppo_seq; Type: SEQUENCE OWNED BY; Schema: savingmoneyunina_schema; Owner: postgres
--

ALTER SEQUENCE savingmoneyunina_schema.gruppo_id_gruppo_seq OWNED BY savingmoneyunina_schema.gruppo.id_gruppo;


--
-- Name: transazione; Type: TABLE; Schema: savingmoneyunina_schema; Owner: postgres
--

CREATE TABLE savingmoneyunina_schema.transazione (
    id_transazione integer NOT NULL,
    data date DEFAULT CURRENT_DATE NOT NULL,
    importo numeric(10,2) NOT NULL,
    tipo_transazione savingmoneyunina_schema.enum_tipo_transazione,
    numero_carta savingmoneyunina_schema.tipo_numero_carta NOT NULL,
    id_gruppo integer
);


ALTER TABLE savingmoneyunina_schema.transazione OWNER TO postgres;

--
-- Name: transazione_id_transazione_seq; Type: SEQUENCE; Schema: savingmoneyunina_schema; Owner: postgres
--

CREATE SEQUENCE savingmoneyunina_schema.transazione_id_transazione_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE savingmoneyunina_schema.transazione_id_transazione_seq OWNER TO postgres;

--
-- Name: transazione_id_transazione_seq; Type: SEQUENCE OWNED BY; Schema: savingmoneyunina_schema; Owner: postgres
--

ALTER SEQUENCE savingmoneyunina_schema.transazione_id_transazione_seq OWNED BY savingmoneyunina_schema.transazione.id_transazione;


--
-- Name: utente; Type: TABLE; Schema: savingmoneyunina_schema; Owner: postgres
--

CREATE TABLE savingmoneyunina_schema.utente (
    id_utente integer NOT NULL,
    nome_utente character varying(20),
    email character varying(50),
    password character varying(20) NOT NULL,
    nome character varying(20) NOT NULL,
    cognome character varying(20) NOT NULL,
    id_famiglia integer
);


ALTER TABLE savingmoneyunina_schema.utente OWNER TO postgres;

--
-- Name: utente_id_utente_seq; Type: SEQUENCE; Schema: savingmoneyunina_schema; Owner: postgres
--

CREATE SEQUENCE savingmoneyunina_schema.utente_id_utente_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE savingmoneyunina_schema.utente_id_utente_seq OWNER TO postgres;

--
-- Name: utente_id_utente_seq; Type: SEQUENCE OWNED BY; Schema: savingmoneyunina_schema; Owner: postgres
--

ALTER SEQUENCE savingmoneyunina_schema.utente_id_utente_seq OWNED BY savingmoneyunina_schema.utente.id_utente;


--
-- Name: famiglia id_famiglia; Type: DEFAULT; Schema: savingmoneyunina_schema; Owner: postgres
--

ALTER TABLE ONLY savingmoneyunina_schema.famiglia ALTER COLUMN id_famiglia SET DEFAULT nextval('savingmoneyunina_schema.famiglia_id_famiglia_seq'::regclass);


--
-- Name: gruppo id_gruppo; Type: DEFAULT; Schema: savingmoneyunina_schema; Owner: postgres
--

ALTER TABLE ONLY savingmoneyunina_schema.gruppo ALTER COLUMN id_gruppo SET DEFAULT nextval('savingmoneyunina_schema.gruppo_id_gruppo_seq'::regclass);


--
-- Name: transazione id_transazione; Type: DEFAULT; Schema: savingmoneyunina_schema; Owner: postgres
--

ALTER TABLE ONLY savingmoneyunina_schema.transazione ALTER COLUMN id_transazione SET DEFAULT nextval('savingmoneyunina_schema.transazione_id_transazione_seq'::regclass);


--
-- Name: utente id_utente; Type: DEFAULT; Schema: savingmoneyunina_schema; Owner: postgres
--

ALTER TABLE ONLY savingmoneyunina_schema.utente ALTER COLUMN id_utente SET DEFAULT nextval('savingmoneyunina_schema.utente_id_utente_seq'::regclass);


--
-- Data for Name: carta; Type: TABLE DATA; Schema: savingmoneyunina_schema; Owner: postgres
--

COPY savingmoneyunina_schema.carta (numero, cvv, scadenza, tipo_carta, saldo, id_utente) FROM stdin;
0123456789123456	543	2025-12-31	Debito	12395.67	4
9123456789123456	743	2026-03-21	Credito	659.97	5
0123456012343452	500	2024-09-15	Debito	54095.00	6
\.


--
-- Data for Name: famiglia; Type: TABLE DATA; Schema: savingmoneyunina_schema; Owner: postgres
--

COPY savingmoneyunina_schema.famiglia (id_famiglia, nome) FROM stdin;
2	Famiglia1
\.


--
-- Data for Name: gruppo; Type: TABLE DATA; Schema: savingmoneyunina_schema; Owner: postgres
--

COPY savingmoneyunina_schema.gruppo (id_gruppo, categoria, nome) FROM stdin;
1	Bollette	g_bollette
2	Spese_mediche	g_spese_mediche
3	Stipendio	g_stipendio
4	Svago	g_svago
\.


--
-- Data for Name: transazione; Type: TABLE DATA; Schema: savingmoneyunina_schema; Owner: postgres
--

COPY savingmoneyunina_schema.transazione (id_transazione, data, importo, tipo_transazione, numero_carta, id_gruppo) FROM stdin;
1	2024-03-28	41.22	Uscita	0123456789123456	4
2	2024-03-28	91.22	Entrata	0123456789123456	1
3	2024-03-28	10.20	Uscita	9123456789123456	\N
4	2024-03-28	140.00	Uscita	0123456012343452	\N
\.


--
-- Data for Name: utente; Type: TABLE DATA; Schema: savingmoneyunina_schema; Owner: postgres
--

COPY savingmoneyunina_schema.utente (id_utente, nome_utente, email, password, nome, cognome, id_famiglia) FROM stdin;
6	c3nzo	vincenzo.esposito109@studenti.unina.it	password	Vincenzo	Esposito	\N
4	taekwondodev	da.galdiero@studenti.unina.it	password	Davide	Galdiero	2
5	raf	raffaelapirozzi@gmail.com	password	Raffaela	Pirozzi	2
\.


--
-- Name: famiglia_id_famiglia_seq; Type: SEQUENCE SET; Schema: savingmoneyunina_schema; Owner: postgres
--

SELECT pg_catalog.setval('savingmoneyunina_schema.famiglia_id_famiglia_seq', 2, true);


--
-- Name: gruppo_id_gruppo_seq; Type: SEQUENCE SET; Schema: savingmoneyunina_schema; Owner: postgres
--

SELECT pg_catalog.setval('savingmoneyunina_schema.gruppo_id_gruppo_seq', 4, true);


--
-- Name: transazione_id_transazione_seq; Type: SEQUENCE SET; Schema: savingmoneyunina_schema; Owner: postgres
--

SELECT pg_catalog.setval('savingmoneyunina_schema.transazione_id_transazione_seq', 4, true);


--
-- Name: utente_id_utente_seq; Type: SEQUENCE SET; Schema: savingmoneyunina_schema; Owner: postgres
--

SELECT pg_catalog.setval('savingmoneyunina_schema.utente_id_utente_seq', 6, true);


--
-- Name: famiglia pk_famiglia; Type: CONSTRAINT; Schema: savingmoneyunina_schema; Owner: postgres
--

ALTER TABLE ONLY savingmoneyunina_schema.famiglia
    ADD CONSTRAINT pk_famiglia PRIMARY KEY (id_famiglia);


--
-- Name: gruppo pk_gruppo; Type: CONSTRAINT; Schema: savingmoneyunina_schema; Owner: postgres
--

ALTER TABLE ONLY savingmoneyunina_schema.gruppo
    ADD CONSTRAINT pk_gruppo PRIMARY KEY (id_gruppo);


--
-- Name: transazione pk_id_transazione; Type: CONSTRAINT; Schema: savingmoneyunina_schema; Owner: postgres
--

ALTER TABLE ONLY savingmoneyunina_schema.transazione
    ADD CONSTRAINT pk_id_transazione PRIMARY KEY (id_transazione);


--
-- Name: carta pk_numero; Type: CONSTRAINT; Schema: savingmoneyunina_schema; Owner: postgres
--

ALTER TABLE ONLY savingmoneyunina_schema.carta
    ADD CONSTRAINT pk_numero PRIMARY KEY (numero);


--
-- Name: utente pk_utente; Type: CONSTRAINT; Schema: savingmoneyunina_schema; Owner: postgres
--

ALTER TABLE ONLY savingmoneyunina_schema.utente
    ADD CONSTRAINT pk_utente PRIMARY KEY (id_utente);


--
-- Name: utente unique_email; Type: CONSTRAINT; Schema: savingmoneyunina_schema; Owner: postgres
--

ALTER TABLE ONLY savingmoneyunina_schema.utente
    ADD CONSTRAINT unique_email UNIQUE (email);


--
-- Name: utente unique_nome_utente; Type: CONSTRAINT; Schema: savingmoneyunina_schema; Owner: postgres
--

ALTER TABLE ONLY savingmoneyunina_schema.utente
    ADD CONSTRAINT unique_nome_utente UNIQUE (nome_utente);


--
-- Name: transazione aggiorna_saldo; Type: TRIGGER; Schema: savingmoneyunina_schema; Owner: postgres
--

CREATE TRIGGER aggiorna_saldo AFTER INSERT ON savingmoneyunina_schema.transazione FOR EACH ROW EXECUTE FUNCTION savingmoneyunina_schema.aggiorna_saldo();


--
-- Name: transazione verifica_saldo; Type: TRIGGER; Schema: savingmoneyunina_schema; Owner: postgres
--

CREATE TRIGGER verifica_saldo BEFORE INSERT ON savingmoneyunina_schema.transazione FOR EACH ROW EXECUTE FUNCTION savingmoneyunina_schema.verifica_saldo();


--
-- Name: utente fk_id_famiglia; Type: FK CONSTRAINT; Schema: savingmoneyunina_schema; Owner: postgres
--

ALTER TABLE ONLY savingmoneyunina_schema.utente
    ADD CONSTRAINT fk_id_famiglia FOREIGN KEY (id_famiglia) REFERENCES savingmoneyunina_schema.famiglia(id_famiglia) ON UPDATE CASCADE;


--
-- Name: transazione fk_id_gruppo; Type: FK CONSTRAINT; Schema: savingmoneyunina_schema; Owner: postgres
--

ALTER TABLE ONLY savingmoneyunina_schema.transazione
    ADD CONSTRAINT fk_id_gruppo FOREIGN KEY (id_gruppo) REFERENCES savingmoneyunina_schema.gruppo(id_gruppo) ON UPDATE CASCADE;


--
-- Name: carta fk_id_utente; Type: FK CONSTRAINT; Schema: savingmoneyunina_schema; Owner: postgres
--

ALTER TABLE ONLY savingmoneyunina_schema.carta
    ADD CONSTRAINT fk_id_utente FOREIGN KEY (id_utente) REFERENCES savingmoneyunina_schema.utente(id_utente) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: transazione fk_numero_carta; Type: FK CONSTRAINT; Schema: savingmoneyunina_schema; Owner: postgres
--

ALTER TABLE ONLY savingmoneyunina_schema.transazione
    ADD CONSTRAINT fk_numero_carta FOREIGN KEY (numero_carta) REFERENCES savingmoneyunina_schema.carta(numero);


--
-- PostgreSQL database dump complete
--

