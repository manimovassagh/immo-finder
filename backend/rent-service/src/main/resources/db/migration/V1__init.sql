--
-- PostgreSQL database dump
--

-- Dumped from database version 15.4 (Debian 15.4-2.pgdg120+1)
-- Dumped by pg_dump version 15.4 (Debian 15.4-2.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: address; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.address (
    id uuid NOT NULL,
    city character varying(255) NOT NULL,
    country character varying(255) NOT NULL,
    number character varying(255) NOT NULL,
    postal_code character varying(255) NOT NULL,
    street character varying(255) NOT NULL
);


ALTER TABLE public.address OWNER TO postgres;

--
-- Name: apartment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.apartment (
    id uuid NOT NULL,
    additional_costs numeric(38,2) NOT NULL,
    area numeric(38,2) NOT NULL,
    cold_rent numeric(38,2) NOT NULL,
    description character varying(255) NOT NULL,
    is_available boolean NOT NULL,
    number_of_bathrooms integer NOT NULL,
    number_of_rooms integer NOT NULL,
    title character varying(255) NOT NULL,
    total_price numeric(38,2) NOT NULL,
    user_id character varying(255) NOT NULL,
    address_id uuid NOT NULL
);


ALTER TABLE public.apartment OWNER TO postgres;

--
-- Name: address address_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.address
    ADD CONSTRAINT address_pkey PRIMARY KEY (id);


--
-- Name: apartment apartment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.apartment
    ADD CONSTRAINT apartment_pkey PRIMARY KEY (id);


--
-- Name: apartment uk_o9dkvj3pb67k9rx211ubphog7; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.apartment
    ADD CONSTRAINT uk_o9dkvj3pb67k9rx211ubphog7 UNIQUE (address_id);


--
-- Name: apartment fkewm9sknyj3o4ss02td055orh0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.apartment
    ADD CONSTRAINT fkewm9sknyj3o4ss02td055orh0 FOREIGN KEY (address_id) REFERENCES public.address(id);


--
-- PostgreSQL database dump complete
--

