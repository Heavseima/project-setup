/**
 * Cross-cutting building blocks every module may use: base entity, API response wrappers, exceptions and the
 * current-user helper. No business logic lives here.
 */
@ApplicationModule(displayName = "Shared", type = ApplicationModule.Type.OPEN)
package com.kshrd.hrdintelligence.shared;

import org.springframework.modulith.ApplicationModule;
