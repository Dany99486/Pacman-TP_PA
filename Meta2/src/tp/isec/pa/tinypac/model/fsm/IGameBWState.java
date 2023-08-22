package tp.isec.pa.tinypac.model.fsm;

//alterar
public interface IGameBWState {
    /**
     * Inicializa a classe GameData
     *
     * @param save Determina se se deve iniciar com o save ou nao
     */
    void start(boolean save);

    /**
     * Retorna o estado da máquina de estados
     *
     * @return estado atual
     */
    GameState getState();

    /**
     * Altera o GameData permitindo o avanço do jogo independente do estado atual(HauntState,WaitState)
     *
     * @param timePassed Tempo passado desde o inicio do jogo
     *
     */
    void action(long timePassed);

    /**
     * Permite que o usuário ao pressionar uma tecla desencadeie um açao
     *
     * @param key Inteiro que representa uma tecla premida na UI
     */
    void setKey(int key);
}
