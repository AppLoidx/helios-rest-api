package com.apploidxxx.api.exceptions;

/**
 *
 * Класс для связки интерфейса {@link IResponsibleException} и {@link Exception}
 *
 * Следует наследовать исключения от этого класса вместо прямой реализации интерфейса {@link IResponsibleException}
 *
 * @author Arthur Kupriyanov
 */
public abstract class ResponsibleException extends Exception implements IResponsibleException {
}
