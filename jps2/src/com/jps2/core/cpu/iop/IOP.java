package com.jps2.core.cpu.iop;

import com.jps2.core.cpu.ExcCode;
import com.jps2.core.cpu.Processor;
import com.jps2.core.cpu.Common.Instruction;
import com.jps2.core.cpu.iop.state.CpuState;

public class IOP extends Processor {

	public IOP() {
		super(new CpuState(), new IOPMemoryManager());
	}

	@Override
	protected Instruction decode(final int opcode) {
		return IOPDecoder.instruction(opcode);
	}

	@Override
	public void processException(final ExcCode e, final int inst, final boolean delay) {
		System.err.println(e);
		// if (e == ExcCode.TRAP) {
		// return;
		// } else if (e == ExcCode.SYSCALL) {
		// System.err.println("SYSCALL");
		// return;
		// }

		// switch (e.getCode()) {
		// case SYSCALL:
		// final int code = ((e.getInstruction() >> 6) & 0xFFFFF);
		// cpu.pc = 0x80000000 | code;
		// System.err.println(Integer.toHexString(code));
		// break;
		// }

		// psxRegs.CP0.n.Cause &= ~0x7f;
		// psxRegs.CP0.n.Cause |= code;
		//
		// // // Set the EPC & PC
		// if (delay) {
		// // psxRegs.CP0.n.Cause|= 0x80000000;
		// cpu.npc = cpu.pc - 4;
		// } else {
		// cpu.npc = cpu.pc;
		// }
		//
		// if (cpu.status.isBev()) {
		// cpu.pc = 0xbfc00180;
		// } else {
		// cpu.pc = 0x80000080;
		// }
		//
		// // Set the Status
		// cpu.status.setRawValue((cpu.status.getRawValue() & ~0x3f) |
		// (cpu.status.getRawValue() & 0xf) << 2);
		//
		// final int call = (int) (cpu.gpr[GprState.T1] & 0xff);
		// switch (cpu.pc & 0x1fffff) {
		// case 0xa0:
		// // if (biosA0[call]){
		// // biosA0[call]();
		// // }
		// break;
		// case 0xb0:
		// // if (biosB0[call])
		// // biosB0[call]();
		// break;
		// case 0xc0:
		//
		// // if (biosC0[call])
		// // biosC0[call]();
		// break;
		// }
	}
}
