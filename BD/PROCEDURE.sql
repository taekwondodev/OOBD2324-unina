SET search_path TO SavingMoneyUnina_schema, public;

CREATE OR REPLACE PROCEDURE inserisci_famiglia_aggiorna_utente(nome_famiglia VARCHAR(20), n_utente VARCHAR(20))
LANGUAGE 'plpgsql'
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