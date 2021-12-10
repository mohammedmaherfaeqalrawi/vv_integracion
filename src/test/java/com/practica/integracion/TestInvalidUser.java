package com.practica.integracion;

import com.practica.integracion.DAO.AuthDAO;
import com.practica.integracion.DAO.GenericDAO;
import com.practica.integracion.DAO.User;
import com.practica.integracion.manager.SystemManager;
import com.practica.integracion.manager.SystemManagerException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestInvalidUser {
    @Mock
    private static AuthDAO mockAuthDao;
    @Mock
    private static GenericDAO mockGenericDao;
    @Test
    public void testStartRemoteSystemWithInValidUser() throws Exception {
        User invalidUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList()));
        when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(invalidUser);
        String validId = "12345"; // id valido de sistema
        when(mockGenericDao.getSomeData(invalidUser, "where id=" + validId)).thenThrow(new OperationNotSupportedException());
        InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
        SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
        SystemManagerException thrown = assertThrows(SystemManagerException.class,() ->{manager.startRemoteSystem(invalidUser.getId(),validId);});
        assertTrue(thrown.getCause() instanceof OperationNotSupportedException);
        ordered.verify(mockAuthDao).getAuthData(invalidUser.getId());
        ordered.verify(mockGenericDao).getSomeData(invalidUser, "where id=" + validId);
    }
    @Test
    public void testStopRemoteSystemWithInValidUser() throws Exception {
        User invalidUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList()));
        when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(invalidUser);
        String validId = "12345"; // id valido de sistema
        when(mockGenericDao.getSomeData(invalidUser, "where id=" + validId)).thenThrow(new OperationNotSupportedException());
        InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
        SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
        SystemManagerException thrown = assertThrows(SystemManagerException.class,() ->{manager.stopRemoteSystem(invalidUser.getId(),validId);});
        assertTrue(thrown.getCause() instanceof OperationNotSupportedException);
        ordered.verify(mockAuthDao).getAuthData(invalidUser.getId());
        ordered.verify(mockGenericDao).getSomeData(invalidUser, "where id=" + validId);
    }
    @Test
    public void testAddRemoteSystemWithInValidUser() throws Exception {
        User invalidUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList()));
        when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(invalidUser);
        ArrayList<Object> lista = new ArrayList<>(Arrays.asList("uno", "dos"));
        when(mockGenericDao.updateSomeData(invalidUser, lista)).thenThrow(new OperationNotSupportedException());
        InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
        SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
        SystemManagerException thrown = assertThrows(SystemManagerException.class,() ->{manager.addRemoteSystem(invalidUser.getId(),lista);});
        assertTrue(thrown.getCause() instanceof OperationNotSupportedException);
        ordered.verify(mockAuthDao).getAuthData(invalidUser.getId());
        ordered.verify(mockGenericDao).updateSomeData(invalidUser, lista);
    }
    @Test
    public void testAddRemoteSystemWithUnAddableRemote() throws Exception {
        User invalidUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList()));
        when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(invalidUser);
        ArrayList<Object> lista = new ArrayList<>(Arrays.asList("uno", "dos"));
        when(mockGenericDao.updateSomeData(invalidUser, lista)).thenReturn(false);
        InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
        SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
        SystemManagerException thrown = assertThrows(SystemManagerException.class,() ->{manager.addRemoteSystem(invalidUser.getId(),lista);});
        assertEquals("cannot add remote",thrown.getMessage());
        ordered.verify(mockAuthDao).getAuthData(invalidUser.getId());
        ordered.verify(mockGenericDao).updateSomeData(invalidUser, lista);
    }
    @Test
    public void testDeleteRemoteSystemWithInvalidUser() throws  Exception {
        User invalidUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList()));
        when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(invalidUser);
        String validId = "12345";
        when(mockGenericDao.deleteSomeData(invalidUser,validId)).thenThrow(new OperationNotSupportedException());
        SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
        manager.deleteRemoteSystem(invalidUser.getId(),validId);
        SystemManagerException thrown = assertThrows(SystemManagerException.class,() ->{manager.addRemoteSystem(invalidUser.getId(),validId);});
        assertTrue(thrown.getCause() instanceof OperationNotSupportedException);
        verify(mockGenericDao, times(1)).deleteSomeData(invalidUser,validId);
    }
    @Test
    public void testDeleteRemoteSystemWithInvalidRemote() throws  Exception {
        User validUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList()));
        when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);
        String invalidId = "12345";
        when(mockGenericDao.deleteSomeData(validUser,invalidId)).thenThrow(new OperationNotSupportedException());
        SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
        manager.deleteRemoteSystem(validUser.getId(),invalidId);
        SystemManagerException thrown = assertThrows(SystemManagerException.class,() ->{manager.addRemoteSystem(validUser.getId(),invalidId);});
        assertEquals("cannot delete remote: does remote exists?",thrown.getMessage());
        verify(mockGenericDao, times(1)).deleteSomeData(validUser,invalidId);
    }

    /**
     * RELLENAR POR EL ALUMNO
     */
}