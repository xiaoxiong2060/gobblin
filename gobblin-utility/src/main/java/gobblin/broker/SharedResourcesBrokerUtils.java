/*
 * Copyright (C) 2014-2017 LinkedIn Corp. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied.
 */

package gobblin.broker;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import gobblin.broker.iface.ScopeType;


/**
 * General utilities for {@link gobblin.broker.iface.SharedResourcesBroker} functionality.
 */
public class SharedResourcesBrokerUtils {

  /**
   * Determine if a {@link ScopeType} is an ancestor of another {@link ScopeType}.
   */
  public static <S extends ScopeType<S>> boolean isScopeTypeAncestor(S scopeType, S possibleAncestor) {
    Queue<S> ancestors = new LinkedList<>();
    ancestors.add(scopeType);
    while (true) {
      if (ancestors.isEmpty()) {
        return false;
      }
      if (ancestors.peek().equals(possibleAncestor)) {
        return true;
      }
      Collection<S> parentScopes = ancestors.poll().parentScopes();
      if (parentScopes != null) {
        ancestors.addAll(parentScopes);
      }
    }
  }

  /**
   * Determine if a {@link ScopeImpl} is an ancestor of another {@link ScopeImpl}.
   */
  static <S extends ScopeType<S>> boolean isScopeAncestor(ScopeImpl<S> scope, ScopeImpl<S> possibleAncestor) {
    Queue<ScopeImpl<S>> ancestors = new LinkedList<>();
    ancestors.add(scope);
    while (true) {
      if (ancestors.isEmpty()) {
        return false;
      }
      if (ancestors.peek().equals(possibleAncestor)) {
        return true;
      }
      ancestors.addAll(ancestors.poll().getParentScopes());
    }
  }
}