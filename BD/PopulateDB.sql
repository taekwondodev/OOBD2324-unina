SET search_path TO SavingMoneyUnina_schema, public;

INSERT INTO Utente(nome_utente, email, password, nome, cognome) 
VALUES('taekwondodev', 'da.galdiero@studenti.unina.it', 'password', 'Davide', 'Galdiero');
INSERT INTO Utente(nome_utente, email, password, nome, cognome)
VALUES('raf', 'raffaelapirozzi@gmail.com', 'password', 'Raffaela', 'Pirozzi');
INSERT INTO Utente(nome_utente, email, password, nome, cognome)
VALUES('c3nzo', 'vincenzo.esposito109@studenti.unina.it', 'password', 'Vincenzo', 'Esposito');

INSERT INTO Carta(numero, cvv, scadenza, tipo_carta, saldo, id_utente)
VALUES('0123456789123456', '543', '2025-12-31', 'Debito', 12345.67, (SELECT id_utente FROM Utente WHERE nome_utente = 'taekwondodev'));
INSERT INTO Carta(numero, cvv, scadenza, tipo_carta, saldo, id_utente)
VALUES('9123456789123456', '743', '2026-03-21', 'Credito', 670.17, (SELECT id_utente FROM Utente WHERE nome_utente = 'raf'));
INSERT INTO Carta(numero, cvv, scadenza, tipo_carta, saldo, id_utente)
VALUES('0123456012343452', '500', '2024-09-15', 'Debito', 54235.00, (SELECT id_utente FROM Utente WHERE nome_utente = 'c3nzo'));

CALL inserisci_famiglia_aggiorna_utente('Famiglia1', 'taekwondodev');
UPDATE Utente SET id_famiglia = (SELECT id_famiglia FROM Utente WHERE nome_utente LIKE 'taekwondodev')
                                 WHERE nome_utente LIKE 'raf';
								 
INSERT INTO Transazione(importo, tipo_transazione, numero_carta)
VALUES(41.22, 'Uscita', '0123456789123456');
												
INSERT INTO Gruppo(categoria, nome)
VALUES('Bollette', 'g_bollette');
INSERT INTO Gruppo(categoria, nome)
VALUES('Spese_mediche', 'g_spese_mediche');
INSERT INTO Gruppo(categoria, nome)
VALUES('Stipendio', 'g_stipendio');
INSERT INTO Gruppo(categoria, nome)
VALUES('Svago', 'g_svago');

UPDATE Transazione SET id_gruppo = (SELECT id_gruppo FROM Gruppo WHERE nome LIKE 'g_svago')
                                    WHERE id_transazione = 1;
									
INSERT INTO Transazione(importo, tipo_transazione, numero_carta, id_gruppo)
VALUES(91.22, 'Entrata', '0123456789123456', (SELECT id_gruppo FROM Gruppo WHERE nome LIKE 'g_bollette'));
INSERT INTO Transazione(importo, tipo_transazione, numero_carta)
VALUES(10.20, 'Uscita', '9123456789123456');
INSERT INTO Transazione(importo, tipo_transazione, numero_carta)
VALUES(140.00, 'Uscita', '0123456012343452');