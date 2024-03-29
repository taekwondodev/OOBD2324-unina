SET search_path TO SavingMoneyUnina_schema, public;

CREATE OR REPLACE FUNCTION aggiorna_saldo() RETURNS TRIGGER
LANGUAGE 'plpgsql'
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

CREATE TRIGGER aggiorna_saldo
AFTER INSERT ON Transazione
FOR EACH ROW EXECUTE FUNCTION aggiorna_saldo();


CREATE OR REPLACE FUNCTION verifica_saldo() RETURNS TRIGGER
LANGUAGE 'plpgsql'
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

CREATE TRIGGER verifica_saldo
BEFORE INSERT ON Transazione
FOR EACH ROW EXECUTE FUNCTION verifica_saldo();