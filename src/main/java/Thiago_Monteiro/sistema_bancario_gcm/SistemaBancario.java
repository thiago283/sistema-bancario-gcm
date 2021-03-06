package Thiago_Monteiro.sistema_bancario_gcm;

import java.util.ArrayList;

import Thiago_Monteiro.sistema_bancario_gcm.exception.DebitoException;
import Thiago_Monteiro.sistema_bancario_gcm.exception.DepositoException;
import Thiago_Monteiro.sistema_bancario_gcm.exception.ListaDeContasException;
import Thiago_Monteiro.sistema_bancario_gcm.exception.TransferenciaException;

// Classe para gerenciar sistema bancario
public class SistemaBancario {
	private final ArrayList<Conta> contas = new ArrayList<>();

	// Mostra contas presentes no sistema
	public void mostrarContas() {
		if (contas == null || contas.isEmpty()) {
			throw new ListaDeContasException("Lista vazia.");
		}
		System.out.println("Contas disponiveis: ");
		for (Conta elem : contas) {
			elem.mostrarConta();
		}
	}

	// Procura uma conta por um ID
	public Conta getConta(int id) {
		boolean foundIt = false;
		for (Conta elem : contas) {
			if (elem.getId() == id) {
				foundIt = true;
				return elem;
			}
		}
		if (!foundIt) {
			throw new ListaDeContasException("Nao foi possivel encontrar a conta para ser removida.");
		}
		return null;
	}

	// Procura e remove uma conta por um ID
	public void removerConta(int id) {
		boolean foundIt = false;
		for (Conta elem : contas) {
			if (elem.getId() == id) {
				contas.remove(elem);
				foundIt = true;
			}
		}
		if (!foundIt) {
			throw new ListaDeContasException("Nao foi possivel encontrar a conta para ser removida.");
		}
	}

	// Adiciona uma nova conta
	public void adicionarConta(Conta conta) {
		boolean addIt = true;
		if (conta != null) {
			for (Conta elem : contas) {
				if (conta.getId() == elem.getId()) {
					addIt = false;
				}
			}
			if (addIt) {
				contas.add(conta);
			} else if (!addIt) {
				throw new ListaDeContasException("Esta conta ja existe no sistema.");
			}
		}
	}

	// Checa saldo da conta pelo ID
	public void checarSaldo(int idConta) {
		boolean foundIt = false;
		for (Conta elem : contas) {
			if (elem.getId() == idConta) {
				foundIt = true;
				System.out.println("O saldo da conta e: R$" + elem.getSaldo());
			}
		}
		if (!foundIt) {
			throw new ListaDeContasException("Conta nao encontrada.");
		}
	}

	// Realiza deposito em conta
	public void realizarDeposito(int idConta, double valor) {
		if (valor < 0) {
			throw new DepositoException("Valor negativo.");
		}
		boolean foundIt = false;
		for (Conta elem : contas) {
			if (elem.getId() == idConta) {
				foundIt = true;
				elem.setSaldoDeposito((elem.getSaldoDeposito() + valor));
				elem.setBonus(elem.getBonus() + (valor / 100));
			}
		}
		if (!foundIt) {
			throw new ListaDeContasException("Conta nao encontrada.");
		}
	}

	// Realiza debito em conta
	public void realizarDebito(int idConta, double valor) {
		if (valor < 0.0) {
			throw new DebitoException("Valor negativo.");
		}
		boolean foundIt = false;
		for (Conta elem : contas) {
			if (elem.getId() == idConta) {
				foundIt = true;
				if (elem.getSaldo() < valor) {
					throw new DebitoException("Valor maior do que saldo disponivel.");
				} else if (elem.getSaldo() >= valor) {
					elem.setSaldo((elem.getSaldo() - valor));
				}
			}
		}
		if (!foundIt) {

			throw new ListaDeContasException("Conta nao encontrada.");
		}
	}

	// Realiza transferencia entre contas
	public void realizarTransferencia(int id1, int id2, double valor) {
		boolean foundIt1 = false;
		boolean foundIt2 = false;
		if (valor < 0) {
			throw new TransferenciaException("Valor negativo.");
		}
		for (Conta elem1 : contas) {
			if (elem1.getId() == id1) {
				foundIt1 = true;
				for (Conta elem2 : contas) {
					if (elem2.getId() == id2) {
						foundIt2 = true;
						if (elem1.getSaldo() < valor) {
							throw new TransferenciaException("Saldo insuficiente para transferencia.");
						} else if (elem1.getSaldo() >= valor) {
							elem1.setSaldo(elem1.getSaldo() - valor);
							elem2.setSaldo(elem2.getSaldo() + valor);
						}
					}
				}
			}
		}
		if (!foundIt1) {
			throw new ListaDeContasException("Conta 1 nao encontrada.");
		}
		if (!foundIt2) {
			throw new ListaDeContasException("Conta 2 nao encontrada.");
		}
	}
}
